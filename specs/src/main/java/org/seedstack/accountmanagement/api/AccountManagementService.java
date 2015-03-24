/*
 * Creation : 6 mars 2015
 */
package org.seedstack.accountmanagement.api;

import java.util.Set;

import org.seedstack.business.api.application.annotations.ApplicationService;

@ApplicationService
public interface AccountManagementService {

    void createAccount(String id, String password);

    Set<String> getRoles(String id);

    void addRole(String id, String role);

    void removeRole(String id, String role);

    void replaceRoles(String id, Set<String> roles);
}
