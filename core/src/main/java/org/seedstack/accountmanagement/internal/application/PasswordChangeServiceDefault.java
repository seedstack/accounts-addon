/*
 * Creation : 6 mars 2015
 */
package org.seedstack.accountmanagement.internal.application;

import javax.inject.Inject;

import org.seedstack.accountmanagement.api.IncorrectPasswordException;
import org.seedstack.accountmanagement.api.PasswordChangeService;
import org.seedstack.accountmanagement.internal.domain.account.Account;
import org.seedstack.accountmanagement.internal.domain.account.AccountRepository;
import org.seedstack.seed.crypto.api.hash.Hash;
import org.seedstack.seed.crypto.api.hash.HashingSupport;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.security.api.SecuritySupport;
import org.seedstack.seed.transaction.api.Transactional;

public class PasswordChangeServiceDefault implements PasswordChangeService {

    @Inject
    private HashingSupport hashingSupport;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private SecuritySupport securitySupport;

    @Override
    @Transactional
    @JpaUnit("seed-account-management-domain")
    public void changePassword(String currentPassword, String newPassword) throws IncorrectPasswordException {
        if (!securitySupport.isAuthenticated()) {
            throw new IllegalStateException("Only connected user can change his password");
        }
        String id = securitySupport.getIdentityPrincipal().getPrincipal().toString();
        Account account = accountRepository.load(id);
        if (account == null) {
            throw new IllegalStateException("User not connected with his database id");
        }
        if (!hashingSupport.validatePassword(currentPassword, new Hash(account.getPassword(), account.getSalt()))) {
            throw new IncorrectPasswordException();
        }
        Hash newHash = hashingSupport.createHash(newPassword);
        account.setPassword(newHash.getHashAsString());
        account.setSalt(newHash.getSaltAsString());
        accountRepository.save(account);
    }

}
