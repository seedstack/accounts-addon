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

import org.seedstack.business.api.Service;

/**
 * Service that allows the connected user to change his current password.
 */
@Service
public interface PasswordChangeService {

    /**
     * Changes the password of the connected user
     *
     * @param oldPassword the old password. Must be correct !
     * @param newPassword the new password
     * @throws IncorrectPasswordException if the old password is incorrect
     */
    void changePassword(String oldPassword, String newPassword) throws IncorrectPasswordException;

}
