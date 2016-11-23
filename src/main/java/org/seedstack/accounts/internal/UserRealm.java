/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 26 févr. 2015
 */
package org.seedstack.accounts.internal;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.seedstack.accounts.BaseUser;
import org.seedstack.accounts.User;
import org.seedstack.business.domain.Repository;
import org.seedstack.seed.crypto.HashingService;
import org.seedstack.seed.security.AuthenticationException;
import org.seedstack.seed.security.AuthenticationInfo;
import org.seedstack.seed.security.AuthenticationToken;
import org.seedstack.seed.security.IncorrectCredentialsException;
import org.seedstack.seed.security.Realm;
import org.seedstack.seed.security.RoleMapping;
import org.seedstack.seed.security.RolePermissionResolver;
import org.seedstack.seed.security.UnsupportedTokenException;
import org.seedstack.seed.security.UsernamePasswordToken;
import org.seedstack.seed.security.principals.PrincipalProvider;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

public class UserRealm implements Realm {
    @Inject
    private Injector injector;
    @Inject
    private Repository<BaseUser, String> userRepository;
    @Inject
    private HashingService hashingService;

    @Override
    public Set<String> getRealmRoles(PrincipalProvider<?> identityPrincipal, Collection<PrincipalProvider<?>> otherPrincipals) {
        return getUser(identityPrincipal.getPrincipal().toString()).getRoles();
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        if (!(authToken instanceof UsernamePasswordToken)) {
            throw new UnsupportedTokenException("Only UsernamePasswordToken is supported");
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        if (!getUser(token.getUsername()).isPasswordValid(String.valueOf(token.getPassword()), hashingService)) {
            throw new IncorrectCredentialsException("Invalid password for user " + token.getUsername());
        }
        return new AuthenticationInfo(token.getUsername(), token.getPassword());
    }

    @Override
    public RoleMapping getRoleMapping() {
        return injector.getInstance(Key.get(RoleMapping.class, Names.named(String.format("%s-role-mapping", getClass().getSimpleName()))));
    }

    @Override
    public RolePermissionResolver getRolePermissionResolver() {
        return injector.getInstance(Key.get(RolePermissionResolver.class, Names.named(String.format("%s-role-permission-resolver", getClass().getSimpleName()))));
    }

    @Override
    public Class<? extends AuthenticationToken> supportedToken() {
        return UsernamePasswordToken.class;
    }

    @Transactional
    protected User getUser(String principal) {
        BaseUser user = userRepository.load(principal);
        if (user == null) {
            throw new AuthenticationException("Unable to find user " + principal);
        }
        return user;
    }
}