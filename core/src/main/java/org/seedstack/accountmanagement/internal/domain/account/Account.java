/*
 * Creation : 5 mars 2015
 */
package org.seedstack.accountmanagement.internal.domain.account;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.seedstack.business.jpa.domain.BaseJpaAggregateRoot;

@Entity
@Table(name = "SEED_ACCOUNTS")
public class Account extends BaseJpaAggregateRoot<String> implements Serializable {

    private static final long serialVersionUID = 4203266703741337332L;

    @Id
    private String accountId;

    private String password;

    private String salt;

    @OneToMany(mappedBy = "account", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<Role> roles;

    protected Account() {
    }

    Account(String id) {
        this.accountId = id;
        this.roles = new HashSet<Role>();
    }

    @Override
    public String getEntityId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
