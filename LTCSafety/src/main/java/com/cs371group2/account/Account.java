package com.cs371group2.account;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created on 2017-02-06.
 */
@Entity
public final class Account {
    /**
     * The id associated with this account.
     */
    @Id
    private String id;

    /**
     * The permissions level of this account.
     */
    @Index
    private AccountPermissions permissions;

    public Account(String accountId, AccountPermissions accountPermissions){
        id = accountId;
        permissions = accountPermissions;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public AccountPermissions getPermissions() {return permissions;}

    public void setPermissions(AccountPermissions permissions) {this.permissions = permissions;}

    @Override
    public String toString() {
        return ("Account ID: " + id + " Account Permissions: " + permissions.toString());
    }
}
