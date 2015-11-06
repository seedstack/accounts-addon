/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 26 févr. 2015
 */
package org.seedstack.accounts.internal.realms;

import org.seedstack.accounts.internal.domain.account.Account;
import org.seedstack.accounts.internal.domain.account.AccountRepository;
import org.seedstack.accounts.internal.domain.account.Role;
import org.seedstack.seed.crypto.Hash;
import org.seedstack.seed.crypto.HashingService;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.security.AuthenticationInfo;
import org.seedstack.seed.security.AuthenticationToken;
import org.seedstack.seed.security.Realm;
import org.seedstack.seed.security.RoleMapping;
import org.seedstack.seed.security.RolePermissionResolver;
import org.seedstack.seed.security.UsernamePasswordToken;
import org.seedstack.seed.security.AuthenticationException;
import org.seedstack.seed.security.IncorrectCredentialsException;
import org.seedstack.seed.security.UnknownAccountException;
import org.seedstack.seed.security.UnsupportedTokenException;
import org.seedstack.seed.security.principals.PrincipalProvider;
import org.seedstack.seed.transaction.Transactional;

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
    @JpaUnit("accounts-domain")
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
    @JpaUnit("accounts-domain")
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
