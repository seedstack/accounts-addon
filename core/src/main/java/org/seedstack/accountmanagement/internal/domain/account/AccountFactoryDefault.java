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
 * Creation : 5 mars 2015
 */
package org.seedstack.accountmanagement.internal.domain.account;

import org.seedstack.business.core.domain.base.BaseFactory;

public class AccountFactoryDefault extends BaseFactory<Account> implements AccountFactory {

    @Override
    public Account createAccount(String accountId) {
        return new Account(accountId);
    }

    @Override
    public Account createAccount(String accountId, String password, String salt) {
        Account a = new Account(accountId);
        a.setPassword(password);
        a.setSalt(salt);
        return a;
    }

    @Override
    public Role createRole(String name, Account account) {
        return new Role(name, account);
    }
}
