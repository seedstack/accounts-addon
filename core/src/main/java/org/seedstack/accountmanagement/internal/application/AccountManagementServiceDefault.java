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
 * Creation : 6 mars 2015
 */
package org.seedstack.accountmanagement.internal.application;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.accountmanagement.api.AccountManagementService;
import org.seedstack.accountmanagement.internal.domain.account.Account;
import org.seedstack.accountmanagement.internal.domain.account.AccountFactory;
import org.seedstack.accountmanagement.internal.domain.account.AccountRepository;
import org.seedstack.accountmanagement.internal.domain.account.Role;
import org.seedstack.seed.crypto.api.hash.Hash;
import org.seedstack.seed.crypto.api.hash.HashingSupport;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.transaction.api.Transactional;

/**
 * Default implementation
 */
@Transactional
@JpaUnit("seed-account-management-domain")
public class AccountManagementServiceDefault implements AccountManagementService {

    @Inject
    private HashingSupport hashingSupport;

    @Inject
    private AccountFactory accountFactory;

    @Inject
    private AccountRepository accountRepository;

    @Override
    public void createAccount(String id, String password) {
        Hash hash = hashingSupport.createHash(password);
        Account account = accountFactory.createAccount(id, hash.getHashAsString(), hash.getSaltAsString());
        accountRepository.persist(account);
    }

    @Override
    public Set<String> getRoles(String id) {
        Set<String> roles = new HashSet<String>();
        Account account = accountRepository.load(id);
        for (Role role : account.getRoles()) {
            roles.add(role.getEntityId());
        }
        return roles;
    }

    @Override
    public void addRole(String id, String role) {
        Account account = accountRepository.load(id);
        Set<Role> roles = account.getRoles();
        Role newRole = accountFactory.createRole(role, account);
        if (!roles.contains(newRole)) {
            roles.add(newRole);
        }
        accountRepository.save(account);
    }

    @Override
    public void removeRole(String id, String role) {
        Account account = accountRepository.load(id);
        Set<Role> roles = account.getRoles();
        for (Role currentRole : roles) {
            if (currentRole.getEntityId().equals(role)) {
                roles.remove(currentRole);
                break;
            }
        }
    }

    @Override
    public void replaceRoles(String id, Set<String> roles) {
        Account account = accountRepository.load(id);
        Set<Role> currentRoles = account.getRoles();
        currentRoles.clear();
        for (String role : roles) {
            Role newRole = accountFactory.createRole(role, account);
            currentRoles.add(newRole);
        }
        accountRepository.save(account);
    }

}
