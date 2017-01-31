package com.cs371group2.admin;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created on 2017-01-30.
 */
@Entity
public class Account {

    /** Identifier for the account entity */
    @Id
    private String id;

    /** Current verification status of the account */
    @Index
    private AccountType isVerified;

    public Account(String id, AccountType isVerified) {
        this.id = id;
        this.isVerified = isVerified;
    }

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

    @Override
    public String toString(){
        return "Account:\nID: " + this.id + this.isVerified.toString();
    }
}
