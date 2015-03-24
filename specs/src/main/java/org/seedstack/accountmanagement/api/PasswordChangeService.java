/*
 * Creation : 6 mars 2015
 */
package org.seedstack.accountmanagement.api;

import org.seedstack.business.api.application.annotations.ApplicationService;

@ApplicationService
public interface PasswordChangeService {

    void changePassword(String oldPassword, String newPassword) throws IncorrectPasswordException;

}
