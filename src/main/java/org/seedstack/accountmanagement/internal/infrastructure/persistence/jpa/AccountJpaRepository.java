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
package org.seedstack.accountmanagement.internal.infrastructure.persistence.jpa;

import org.seedstack.accountmanagement.internal.domain.account.Account;
import org.seedstack.accountmanagement.internal.domain.account.AccountRepository;
import org.seedstack.business.jpa.BaseJpaRepository;

/**
 * Jpa implementation of AccountRepository
 */
public class AccountJpaRepository extends BaseJpaRepository<Account, String> implements AccountRepository {

}
