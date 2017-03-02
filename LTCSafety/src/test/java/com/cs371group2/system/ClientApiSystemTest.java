package com.cs371group2.system;

import com.appspot.ltc_safety.client.Client;
import com.appspot.ltc_safety.client.model.ConcernData;
import com.appspot.ltc_safety.client.model.SubmitConcernResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * These tests are related to the concern class to ensure that concern data is properly validated
 * when submitted. This involves checking that the correct responses are returned when unexpected or
 * invalid data is provided during concern submission.
 *
 * NOTE - These tests were originally part of the client API tests. They were moved to take
 * advantage of the package private variables for testing.
 *
 * Created on 2017-01-22.
 */
public class ClientApiSystemTest {

    private static Client client;

    @BeforeClass
    public static void setUp() throws Exception {
        client = new Client.Builder(new NetHttpTransport(), new GsonFactory(), null).build();
    }

    @Test
    @Category(com.cs371group2.system.SystemTests.class)
    public void test() {

        ConcernData data = new ConcernData();

        try {
            SubmitConcernResponse response = client.submitConcern(data).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}