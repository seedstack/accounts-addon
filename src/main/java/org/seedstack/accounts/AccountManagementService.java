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
package org.seedstack.accounts;

import org.seedstack.business.Service;

import java.util.Set;

/**
 * Service to manage accounts. Allows to create new accounts with their roles.
 */
@Service
public interface AccountManagementService {

    /**
     * Creates a new account. The given password will be hashed.
     *
     * @param id       id of the account
     * @param password the clear password. WILL BE HASHED
     */
    void createAccount(String id, String password);

    /**
     * Returns the roles
     *
     * @param id id of the account
     * @return a Set of string representing the roles
     */
    Set<String> getRoles(String id);

    /**
     * Adds a role for an account
     *
     * @param id   id of the account
     * @param role the role to add.
     */
    void addRole(String id, String role);

    /**
     * Removes a role from the account
     *
     * @param id   id of the account
     * @param role the role to remove
     */
    void removeRole(String id, String role);

    /**
     * Replaces all the roles of an account
     *
     * @param id    id of the account
     * @param roles the roles to give
     */
    void replaceRoles(String id, Set<String> roles);
}
