/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 5 mars 2015
 */
package org.seedstack.accounts.internal.domain.account;


import org.seedstack.business.domain.BaseFactory;

/**
 * Default AccountFactory.
 */
public class AccountFactoryDefault extends BaseFactory<Account> implements AccountFactory {

    @Override
    public Account createAccount(String accountId) {
        return new Account(accountId);
    }

    @Override
    public Account createAccount(String accountId, String password, String salt) {
        Account a = new Account(accountId);
        a.setHashedPassword(password);
        a.setSalt(salt);
        return a;
    }
}
