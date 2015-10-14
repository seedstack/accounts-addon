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
 * Creation : 6 mars 2015
 */
package org.seedstack.accountmanagement.api;

/**
 * Exception thrown if the password given is incorrect.
 */
public class IncorrectPasswordException extends Exception {

    private static final long serialVersionUID = -2651046105421381539L;

    /**
     * Constructor
     */
    public IncorrectPasswordException() {
        super();
    }

    /**
     * Constructor
     */
    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     */
    public IncorrectPasswordException(String message) {
        super(message);
    }

    /**
     * Constructor
     */
    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }

}
