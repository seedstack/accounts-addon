/*
 * Creation : 5 mars 2015
 */
package org.seedstack.accountmanagement.internal.domain.account;

import org.seedstack.business.api.domain.GenericFactory;

public interface AccountFactory extends GenericFactory<Account> {

    Account createAccount(String accountId);

    Account createAccount(String accountId, String password, String salt);

    Role createRole(String name, Account account);
}
