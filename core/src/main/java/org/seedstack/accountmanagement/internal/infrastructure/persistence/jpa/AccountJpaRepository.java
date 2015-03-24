/*
 * Creation : 5 mars 2015
 */
package org.seedstack.accountmanagement.internal.infrastructure.persistence.jpa;

import org.seedstack.accountmanagement.internal.domain.account.Account;
import org.seedstack.accountmanagement.internal.domain.account.AccountRepository;
import org.seedstack.business.jpa.infrastructure.repository.BaseJpaRepository;

public class AccountJpaRepository extends BaseJpaRepository<Account, String> implements AccountRepository {

}
