package com.cs371group2.admin;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Account entity for storing information in a database about a user's account
 *
 * Created on 2017-01-30.
 */
@Entity
public class Account {

    /** Identifier for the account entity */
    @Id
    private String id;

    /** Current verification status of the account */
    @Index
    private AccountType accessType;


    /**
     * Creates a new account with the given ID
     * @param id The account ID
     */
    public Account(String id) {
        this.id = id;
        accessType = AccountType.UNVERIFIED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccountType accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString(){
        return "Account:\nID: " + this.id + this.accessType.toString();
    }
}
