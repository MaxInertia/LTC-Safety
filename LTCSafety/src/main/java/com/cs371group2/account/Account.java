package com.cs371group2.account;

import com.cs371group2.facility.Facility;
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

    /**
     * The permissions level of this account.
     */
    @Index
    private AccountPermissions permissions;

    /**
     * The hashset of care home facilities this account is associated with.
     */
    @Index
    private HashSet<Facility> facilities;

    public Account(String accountId, AccountPermissions accountPermissions){
        assert(accountId != null);
        assert(accountPermissions != null);

        id = accountId;
        permissions = accountPermissions;
        facilities = new HashSet<Facility>();
    }

    /**
     * No-arg constructor required for being loaded from the datastore.
     */
    private Account() {}

    public String getId() {return id;}

    public HashSet<Facility> getFacilities() {
        return facilities;
    }

    /**
     * Associates the account with the given facility by adding
     * it to the account's hashset of associated facilities
     *
     * @param toAdd the facility to associate with this account
     * @return Whether the association was successful or not
     */
    public boolean addFacility(Facility toAdd){
        if(toAdd == null)
            return false;
        else
            return facilities.add(toAdd);
    }

    /**
     * Disassociates the account with the given facility by removing
     * it from the account's hashset of associated facilities
     *
     * @param toRemove the facility to associate with this account
     * @return Whether the association was successful or not
     */
    public boolean removeFacility(Facility toRemove){
        if(toRemove == null)
            return false;
        else
            return facilities.remove(toRemove);
    }

    public void setId(String id) {
        assert(id != null);
        this.id = id;
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
