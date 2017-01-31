package com.cs371group2.admin;

/**
 * Created on 2017-01-30.
 */
public class Account {

    /** Identifier for the account entity */
    private String id;

    /** Current verification status of the account */
    private AccountType isVerified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountType getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(AccountType isVerified) {
        this.isVerified = isVerified;
    }
}
