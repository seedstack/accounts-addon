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
