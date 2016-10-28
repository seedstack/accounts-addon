/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 6 mars 2015
 */
package org.seedstack.accounts;

/**
 * Exception thrown if the password given is incorrect.
 */
public class IncorrectPasswordException extends Exception {

    private static final long serialVersionUID = -2651046105421381539L;

    /**
     * Constructor.
     */
    public IncorrectPasswordException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     * @param cause   the exception cause
     */
    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message the exception message
     */
    public IncorrectPasswordException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause the exception cause
     */
    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }

}
