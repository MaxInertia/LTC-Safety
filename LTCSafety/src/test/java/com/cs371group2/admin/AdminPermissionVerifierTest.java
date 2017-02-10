package com.cs371group2.admin;

import com.cs371group2.account.Account;
import org.junit.Test;

/**
 * Created by Brandon on 2017-02-09.
 */
public class AdminPermissionVerifierTest {

    @Test (expected = AssertionError.class)
    public void NullAccountTest(){
        Account account = new AccountTest().generateAccount();
        PermissionVerifier verifier = new AdminPermissionVerifier();
        verifier.hasPermission(account, null);
    }

    @Test (expected = AssertionError.class)
    public void NullAccessTokenTest(){
        AccessToken token = new AccessToken("test@test.com", "asdfghj", "Tester", true);
        PermissionVerifier verifier = new AdminPermissionVerifier();
        verifier.hasPermission(null, token);
    }
}
