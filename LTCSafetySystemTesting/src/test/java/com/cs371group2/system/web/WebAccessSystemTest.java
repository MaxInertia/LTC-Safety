package com.cs371group2.system.web;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by allankerr on 2017-03-02.
 */
public abstract class WebAccessSystemTest extends WebTest {

    protected RemoteWebDriver webDriver;

    @Test
    @Category(WebSystemTests.class)
    public void unknownFacility() throws Exception {

        webDriver.get(getPath());

        //WebElement emailElement = webDriver.findElementById("email");
        //emailElement.sendKeys("email@test.com");

        //WebElement passwordElement = webDriver.findElementById("password");
        //passwordElement.sendKeys("password");
    }

    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.close();
        }
    }
}
