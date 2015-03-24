/*
 * Creation : 9 mars 2015
 */
package org.seedstack.seed.security.db;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.accountmanagement.api.AccountManagementService;
import org.seedstack.accountmanagement.api.IncorrectPasswordException;
import org.seedstack.accountmanagement.api.PasswordChangeService;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.api.SecuritySupport;

@RunWith(SeedITRunner.class)
public class PasswordChangeServiceIT {

    private static final String ID = "Obiwan";

    private static final String PASSWORD = "Y0daRul3z";

    private static final String NEW_PASSWORD = "B3tRayA1";

    private static final String ROLE = "SEED.JEDI";

    @Inject
    private AccountManagementService accountManagementService;

    @Inject
    private PasswordChangeService passwordChangeService;

    @Inject
    private SecuritySupport securitySupport;

    @Inject
    @Named("defaultSecurityManager")
    private SecurityManager securityManager;

    @Before
    public void before() {
        ThreadContext.bind(securityManager);
    }

    @Test
    public void completeTest() throws Exception {
        accountManagementService.createAccount(ID, PASSWORD);
        accountManagementService.addRole(ID, ROLE);
        try {
            passwordChangeService.changePassword(PASSWORD, NEW_PASSWORD);
            Assert.fail("Should have failed as user not connected");
        } catch (IllegalStateException e) {
            // ok
        }
        connectUser(ID, PASSWORD);
        try {
            passwordChangeService.changePassword("dummy", NEW_PASSWORD);
            Assert.fail("Should have failed as wrong password");
        } catch (IncorrectPasswordException e) {
            // ok
        }
        passwordChangeService.changePassword(PASSWORD, NEW_PASSWORD);
        securitySupport.logout();
        assertThat(securitySupport.isAuthenticated()).isFalse();
        connectUser(ID, NEW_PASSWORD);
        assertThat(securitySupport.isAuthenticated()).isTrue();
    }

    private void connectUser(String id, String password) {
        Subject subject = new Subject.Builder().buildSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(id, password);
        subject.login(token);
        ThreadContext.bind(subject);
    }
}