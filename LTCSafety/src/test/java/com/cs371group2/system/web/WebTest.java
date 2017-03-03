package com.cs371group2.system.web;

import org.junit.BeforeClass;

/**
 * Created by allankerr on 2017-03-02.
 */
public abstract class WebTest {

    private static final String LOCAL_HOST_PATH = "http://localhost:8080";

    private static final String APPSPOT_PATH = "https://ltc-safety.appspot.com";

    private static String path;

    public String getPath() {
        return path;
    }

    @BeforeClass
    public static void setUpTestPath() {
        boolean runOnLocalHost = Boolean.valueOf(System.getProperty("local"));
        if (runOnLocalHost) {
            path = LOCAL_HOST_PATH;
        } else {
            path = APPSPOT_PATH;
        }
    }
}
