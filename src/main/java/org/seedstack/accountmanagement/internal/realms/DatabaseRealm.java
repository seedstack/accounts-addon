/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 26 févr. 2015
 */
package org.seedstack.accountmanagement.internal.realms;

import org.seedstack.accountmanagement.internal.domain.account.Account;
import org.seedstack.accountmanagement.internal.domain.account.AccountRepository;
import org.seedstack.accountmanagement.internal.domain.account.Role;
import org.seedstack.seed.crypto.api.Hash;
import org.seedstack.seed.crypto.api.HashingService;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.security.api.AuthenticationInfo;
import org.seedstack.seed.security.api.AuthenticationToken;
import org.seedstack.seed.security.api.Realm;
import org.seedstack.seed.security.api.RoleMapping;
import org.seedstack.seed.security.api.RolePermissionResolver;
import org.seedstack.seed.security.api.UsernamePasswordToken;
import org.seedstack.seed.security.api.exceptions.AuthenticationException;
import org.seedstack.seed.security.api.exceptions.IncorrectCredentialsException;
import org.seedstack.seed.security.api.exceptions.UnknownAccountException;
import org.seedstack.seed.security.api.exceptions.UnsupportedTokenException;
import org.seedstack.seed.security.api.principals.PrincipalProvider;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A Realm to authentify user upon the Accounts persisted by the repository
 */
public class DatabaseRealm implements Realm {

    private RoleMapping roleMapping;

    private RolePermissionResolver rolePermissionResolver;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private HashingService hashingService;

    @Override
    @Transactional
    @JpaUnit("seed-account-management-domain")
    public Set<String> getRealmRoles(PrincipalProvider<?> identityPrincipal, Collection<PrincipalProvider<?>> otherPrincipals) {
        Set<String> roles = new HashSet<String>();
        Account account = accountRepository.load(identityPrincipal.getPrincipal().toString());
        for (Role role : account.getRoles()) {
            roles.add(role.getName());
        }
        return roles;
    }

    @Override
    @Transactional
    @JpaUnit("seed-account-management-domain")
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        if (!(authToken instanceof UsernamePasswordToken)) {
            throw new UnsupportedTokenException();
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        Account account = accountRepository.load(token.getUsername());
        if (account == null) {
            throw new UnknownAccountException("Account with id [" + token.getUsername() + "] does not exist");
        }
        Hash correctHash = new Hash(account.getHashedPassword(), account.getSalt());
        if (!hashingService.validatePassword(token.getPassword(), correctHash)) {
            throw new IncorrectCredentialsException();
        }

        return new AuthenticationInfo(token.getUsername(), token.getPassword());
    }

    @Override
    public RoleMapping getRoleMapping() {
        return this.roleMapping;
    }

    @Override
    public RolePermissionResolver getRolePermissionResolver() {
        return this.rolePermissionResolver;
    }

    /**
     * Setter roleMapping
     *
     * @param roleMapping the role mapping
     */
    @Inject
    public void setRoleMapping(@Named("DatabaseRealm-role-mapping") RoleMapping roleMapping) {
        this.roleMapping = roleMapping;
    }

    /**
     * Setter rolePermissionResolver
     *
     * @param rolePermissionResolver the rolePermissionResolver
     */
    @Inject
    public void setRolePermissionResolver(@Named("DatabaseRealm-role-permission-resolver") RolePermissionResolver rolePermissionResolver) {
        this.rolePermissionResolver = rolePermissionResolver;
    }

    @Override
    public Class<? extends AuthenticationToken> supportedToken() {
        return UsernamePasswordToken.class;
    }

}
