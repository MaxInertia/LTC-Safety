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

        //ConcernDao dao = new ConcernDao();

        //ConcernData concernData = new ConcernTest().generateConcernData();

        //dao.save(new Concern(concernData));

        //AdminApi api = new AdminApi();
        //List<Concern> concerns = api.getConcerns(new ConcernRequest(0, 1, TEST_TOKEN));
        //assertNotNull(concerns);

    }
}
