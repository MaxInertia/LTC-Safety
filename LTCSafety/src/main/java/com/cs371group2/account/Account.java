package com.cs371group2.account;

import com.cs371group2.facility.Facility;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.HashSet;

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



    /**
     * The hashset of care home facilities this account is associated with.
     */
    @Index
    private HashSet<Facility> locations;

    public Account(String accountId, AccountPermissions accountPermissions){
        assert(accountId != null);
        assert(accountPermissions != null);

        id = accountId;
        permissions = accountPermissions;
        locations = new HashSet<Facility>();
    }

    /**
     * No-arg constructor required for being loaded from the datastore.
     */
    private Account() {}

    public String getId() {return id;}

    public HashSet<Facility> getLocations() {
        return locations;
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
