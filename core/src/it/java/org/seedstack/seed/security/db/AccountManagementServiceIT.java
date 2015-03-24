/*
 * Creation : 9 mars 2015
 */
package org.seedstack.seed.security.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.accountmanagement.api.AccountManagementService;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.security.api.SecuritySupport;

@RunWith(SeedITRunner.class)
public class AccountManagementServiceIT {

    private static final String ID = "Obiwan";

    private static final String PASSWORD = "Y0daRul3z";

    private static final String ROLE = "SEED.JEDI";

    @Inject
    private AccountManagementService accountManagementService;

    @Inject
    private SecuritySupport securitySupport;

    @Inject
    @Named("defaultSecurityManager")
    private SecurityManager securityManager;

    private static boolean alreadyInited;

    @Before
    public void initBase() throws Exception {
        if (!alreadyInited) {
            accountManagementService.createAccount(ID, PASSWORD);
            accountManagementService.addRole(ID, ROLE);
            alreadyInited = true;
        }
    }

    @Test
    public void accountHasBeenCreatedWithRoles() {
        Set<String> roles = accountManagementService.getRoles(ID);
        assertThat(roles).containsExactly(ROLE);
    }

    @Test
    public void userCanConnectWithHisPassword() {
        connectUser(ID, PASSWORD);
        assertThat(securitySupport.isAuthenticated()).isTrue();
    }

    private void connectUser(String id, String password) {
        ThreadContext.bind(securityManager);
        Subject subject = new Subject.Builder(securityManager).buildSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(id, password);
        subject.login(token);
        ThreadContext.bind(subject);
    }
}
