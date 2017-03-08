package com.cs371group2.account;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.HashSet;

/**
 * This class represents an account in the system. It contains a permission level, a unique id, and a set of
 * facilities that the account is assigned to.
 *
 * Created on 2017-02-06.
 */
@Entity
public final class Account {

    /**
     * The id associated with this account.
     */
    @Id
    private String id;

    @Index
    private boolean isTestingAccount;

    /**
     * The permissions level of this account.
     */
    @Index
    private AccountPermissions permissions;

    public Account(String id, AccountPermissions permissions){
        this(id, permissions, false);
    }

    /**
     * Construct a new account.
     * @param id The accounts unique identifier.
     * @param permissions The permission level the account has.
     * @param isTestingAccount Whether or not the account is restricted to concerns flagged as test concerns.
     */
    public Account(String id, AccountPermissions permissions, boolean isTestingAccount){

        assert(id != null);
        assert(permissions != null);

        this.id = id;
        this.permissions = permissions;
        this.isTestingAccount = isTestingAccount;
    }

    /**
     * No-arg constructor required for being loaded from the datastore.
     */
    private Account() {}

    public String getId() {return id;}

    public boolean isTestingAccount() {
        return isTestingAccount;
    }

    public AccountPermissions getPermissions() {return permissions;}

    public void setPermissions(AccountPermissions permissions) {
        assert(permissions != null);
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return ("Account ID: " + id + " Account Permissions: " + permissions.toString());
    }
}
