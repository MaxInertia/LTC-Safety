package com.cs371group2.system.web;

import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by William on 2017-03-19.
 */
public class FirefoxAccessSystemTest extends WebAccessSystemTest {

    @BeforeClass
    public static void setUpDriver() {
        FirefoxDriverManager.getInstance().setup();
    }

    @Before
    public void setUp() {
        webDriver = new FirefoxDriver();
    }
}



