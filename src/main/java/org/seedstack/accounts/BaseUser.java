/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.accounts;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.seed.crypto.Hash;
import org.seedstack.seed.crypto.HashingService;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BaseUser extends BaseAggregateRoot<String> implements User {
    @NotNull
    private String id;
    private byte[] hash;
    private byte[] salt;
    private Set<String> roles = new HashSet<>();

    private BaseUser() {
        // required for mappers
    }

    public BaseUser(String id) {
        this.id = id;
    }

    @Override
    public String getEntityId() {
        return null;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean isPasswordValid(String password, HashingService hashingService) {
        return hashingService.validatePassword(password, new Hash(hash, salt));
    }

    public void changePassword(String password, HashingService hashingService) {
        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty passwords are forbidden");
        }
        Hash hash = hashingService.createHash(password);
        this.hash = hash.getHash();
        this.salt = hash.getSalt();
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public void removeRole(String role) {
        roles.remove(role);
    }

    public Set<String> getRoles() {
        return Collections.unmodifiableSet(roles);
    }
}
