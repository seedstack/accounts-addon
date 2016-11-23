/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.accounts;

import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.LifecycleListener;
import org.seedstack.seed.crypto.HashingService;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;

public class DataLifecycleListener implements LifecycleListener {
    @Inject
    private Repository<BaseUser, String> userRepository;
    @Inject
    private HashingService hashingService;

    @Override
    @Transactional
    @JpaUnit("accounts-unit")
    public void started() {
        BaseUser admin = new BaseUser("admin");
        admin.changePassword("toto", hashingService);
        admin.addRole("ADMIN");
        userRepository.persist(admin);
    }

    @Override
    public void stopping() {

    }
}
