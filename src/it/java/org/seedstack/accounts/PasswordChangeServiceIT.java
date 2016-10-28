/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 9 mars 2015
 */
package org.seedstack.accounts;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.security.SecuritySupport;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordChangeServiceIT extends AbstractSeedIT{

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
