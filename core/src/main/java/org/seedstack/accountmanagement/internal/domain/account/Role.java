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
 * Creation : 5 mars 2015
 */
package org.seedstack.accountmanagement.internal.domain.account;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Table;

import org.seedstack.business.api.domain.base.BaseValueObject;

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
