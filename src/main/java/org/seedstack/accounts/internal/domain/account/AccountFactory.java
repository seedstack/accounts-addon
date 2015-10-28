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

import org.seedstack.business.api.domain.GenericFactory;

/**
 * Factory used to create a new {@link Account}
 */
public interface AccountFactory extends GenericFactory<Account> {

    /**
     * Creates a new {@link Account} with its id
     *
     * @param accountId the id
     * @return a new {@link Account}
     */
    Account createAccount(String accountId);

    /**
     * Creates a new {@link Account} with id, hashed password and salt
     *
     * @param accountId      id
     * @param hashedPassword the hashed password
     * @param salt           the salt used to hash the password
     * @return the new {@link Account}
     */
    Account createAccount(String accountId, String hashedPassword, String salt);
}
