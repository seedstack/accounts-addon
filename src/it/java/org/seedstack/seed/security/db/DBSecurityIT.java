/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 27 févr. 2015
 */
package org.seedstack.seed.security.db;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.accountmanagement.internal.domain.account.Account;
import org.seedstack.accountmanagement.internal.domain.account.AccountFactory;
import org.seedstack.accountmanagement.internal.domain.account.AccountRepository;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.security.api.SecuritySupport;
import org.seedstack.seed.transaction.api.Transactional;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SeedITRunner.class)
public class DBSecurityIT {

    private static final String ID = "Obiwan";

    private static final String PASSWORD = "Y0daRuleZ";

    @Inject
    private AccountFactory accountFactory;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private SecuritySupport securitySupport;

    @Inject
    private SecurityManager securityManager;

    private static boolean alreadyInited;

    @Transactional
    @JpaUnit("seed-account-management-domain")
    @Before
    public void initBase() throws Exception {
        ThreadContext.bind(securityManager);
        if (!alreadyInited) {
            Account account = accountFactory.createAccount(ID, "5BCBD689FA503E51D3DC7A1D711EE8D851F6A70F46A83FCF",
                    "2C80E0E3779909FA6335B25EC1D4470316630D210754317E");
            account.addRole("SEED.JEDI");
            accountRepository.persist(account);
            alreadyInited = true;
        }
    }

    @Test
    public void goodCredentials() {
        connectUser(ID, PASSWORD);
        assertThat(securitySupport.isAuthenticated()).isTrue();
        assertThat(securitySupport.hasRole("jedi")).isTrue();
        securitySupport.logout();
    }

    @Test(expected = AuthenticationException.class)
    public void wrongPassword() {
        connectUser(ID, "S0mePa55");
        securitySupport.logout();
    }

    private void connectUser(String id, String password) {
        Subject subject = new Subject.Builder().buildSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(id, password);
        subject.login(token);
        ThreadContext.bind(subject);
    }

}
