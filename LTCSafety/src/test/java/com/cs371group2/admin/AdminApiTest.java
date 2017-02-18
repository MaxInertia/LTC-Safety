package com.cs371group2.admin;

import com.cs371group2.concern.Concern;
import com.cs371group2.concern.ConcernDao;
import com.cs371group2.concern.ConcernData;
import com.cs371group2.concern.ConcernTest;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created on 2017-02-09.
 */
public class AdminApiTest {

    @Test
    public void getConcernsTest() throws GeneralSecurityException, IOException {
    /*
        ConcernDao dao = new ConcernDao();

        ConcernData.TestHook_MutableConcernData concernData =
        new ConcernTest().generateConcernData();
        dao.save(new Concern(concernData.build()));

        AdminApi api = new AdminApi();
        List<Concern> concerns = api.requestConcernList(new ConcernRequest(0, 1, "x5gvMAYGfNcKv74VyHFgr4Ytcge2"));
        assertNotNull(concerns);
        */
    }
}
