package com.cs371group2.account;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

/**
 * This class represents an account in the system. It contains a permission level, a unique id, and a set of
 * facilities that the account is assigned to.
 *
 * History property: Instances of this class are mutable from the time they are created. However, the id and email
 * are immutable from the time the account is created.
 *
 * Invariance properties: This class assumes the provided information (email, id, etc) is properly formatted, and that
 * the isEmailVerified field accurately indicates whether the email has been verified or not.
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

    /**
     * The email that the account was created with.
     */
    private String email;

    /**
     * Whether or not the email is verified. This is not stored in the datastore
     * because the email verification status is contained in the access token when the account is loaded.
     */
    @Ignore
    private boolean isEmailVerified;

    @Index
    private boolean isTestingAccount;

    /**
     * The permissions level of this account.
     */
    @Index
    private AccountPermissions permissions;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    /**
     * Setter for isEmailVerified to be used when the account is loaded using a new access token.
     * The email verified status is provided by the access token.
     * @param emailVerified
     */
    void setEmailVerified(boolean emailVerified) {
        this.isEmailVerified = emailVerified;
    }

    public boolean isTestingAccount() {
        return isTestingAccount;
    }

    public AccountPermissions getPermissions() {
        return permissions;
    }

    public void setPermissions(AccountPermissions permissions) {
        assert(permissions != null);
        this.permissions = permissions;
    }


    /**
     * Construct a new account.
     * @param id The accounts unique identifier.
     * @param email The email that the account was created with.
     * @param permissions The permission level the account has.
     * @precond id != null && !id.isEmpty()
     * @precond email != null && !email.isEmpty()
     * @precond permissions != null
     */
    public Account(String id, String email, AccountPermissions permissions){
        this(id, email, permissions, false);
    }

    /**
     * Construct a new account.
     * @param id The accounts unique identifier.
     * @param email The email that the account was created with.
     * @param permissions The permission level the account has.
     * @param isTestingAccount Whether or not the account is restricted to concerns flagged as test concerns.
     * @precond id != null && !id.isEmpty()
     * @precond email != null && !email.isEmpty()
     * @precond permissions != null
     */
    public Account(String id, String email, AccountPermissions permissions, boolean isTestingAccount){

        assert(id != null && !id.isEmpty());
        assert(email != null && !email.isEmpty());
        assert(permissions != null);

        this.id = id;
        this.email = email;
        this.permissions = permissions;
        this.isTestingAccount = isTestingAccount;
    }

    /**
     * No-arg constructor required for being loaded from the datastore.
     */
    private Account() {}

    @Override
    public String toString() {
        return ("Account ID: " + id + " Account Permissions: " + permissions.toString());
    }
}
