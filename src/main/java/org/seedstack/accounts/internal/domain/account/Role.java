/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 5 mars 2015
 */
package org.seedstack.accounts.internal.domain.account;


import org.seedstack.business.api.domain.BaseValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A Role is only a String representing its name
 */
@Embeddable
@Table(name = "SEED_ROLES")
public class Role extends BaseValueObject implements Serializable {

    private static final long serialVersionUID = 3281204012412448078L;

    private String name;

    public String getName() {
        return name;
    }

    protected Role() {
    }

    Role(String role) {
        this.name = role;
    }

}
