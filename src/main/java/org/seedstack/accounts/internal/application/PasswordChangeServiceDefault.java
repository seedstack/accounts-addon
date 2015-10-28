/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 6 mars 2015
 */
package org.seedstack.accounts.internal.application;

import org.seedstack.accounts.IncorrectPasswordException;
import org.seedstack.accounts.PasswordChangeService;
import org.seedstack.accounts.internal.domain.account.Account;
import org.seedstack.accounts.internal.domain.account.AccountRepository;
import org.seedstack.seed.crypto.api.Hash;
import org.seedstack.seed.crypto.api.HashingService;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.security.api.SecuritySupport;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;

/**
 * Default implementation
 */
public class PasswordChangeServiceDefault implements PasswordChangeService {

    @Inject
    private HashingService hashingService;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private SecuritySupport securitySupport;

    @Override
    @Transactional
    @JpaUnit("accounts-domain")
    public void changePassword(String currentPassword, String newPassword) throws IncorrectPasswordException {
        if (!securitySupport.isAuthenticated()) {
            throw new IllegalStateException("Only connected user can change his password");
        }
        String id = securitySupport.getIdentityPrincipal().getPrincipal().toString();
        Account account = accountRepository.load(id);
        if (account == null) {
            throw new IllegalStateException("User not connected with his database id");
        }
        if (!hashingService.validatePassword(currentPassword, new Hash(account.getHashedPassword(), account.getSalt()))) {
            throw new IncorrectPasswordException();
        }
        Hash newHash = hashingService.createHash(newPassword);
        account.setHashedPassword(newHash.getHashAsString());
        account.setSalt(newHash.getSaltAsString());
        accountRepository.save(account);
    }

}
