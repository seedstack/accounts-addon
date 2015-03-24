/*
 * Creation : 6 mars 2015
 */
package org.seedstack.accountmanagement.api;

public class IncorrectPasswordException extends Exception {

    private static final long serialVersionUID = -2651046105421381539L;

    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }

}
