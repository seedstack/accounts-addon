/*
 * Creation : 5 mars 2015
 */
package org.seedstack.accountmanagement.internal.domain.account;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.seedstack.business.jpa.domain.BaseJpaEntity;

@Entity
@Table(name = "SEED_ROLES")
public class Role extends BaseJpaEntity<String> {

    private static final long serialVersionUID = 3281204012412448078L;

    @Id
    private String role;

    @ManyToOne
    private Account account;

    @Override
    public String getEntityId() {
        return role;
    }

    protected Role() {
    }

    Role(String role, Account account) {
        this.role = role;
        this.account = account;
    }

}
