package com.cs371group2.system.web;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by allankerr on 2017-03-02.
 */
public class ChromeAccessSystemTest extends WebAccessSystemTest {

    @BeforeClass
    public static void setUpDriver() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUp() {
        webDriver = new ChromeDriver();
    }
}
