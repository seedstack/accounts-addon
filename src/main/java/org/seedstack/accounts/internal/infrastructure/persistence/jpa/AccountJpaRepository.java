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
package org.seedstack.accounts.internal.infrastructure.persistence.jpa;

import org.seedstack.accounts.internal.domain.account.Account;
import org.seedstack.accounts.internal.domain.account.AccountRepository;
import org.seedstack.business.jpa.BaseJpaRepository;

/**
 * Jpa implementation of AccountRepository
 */
public class AccountJpaRepository extends BaseJpaRepository<Account, String> implements AccountRepository {

}
