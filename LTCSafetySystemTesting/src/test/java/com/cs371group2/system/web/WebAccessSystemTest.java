package com.cs371group2.system.web;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by allankerr on 2017-03-02.
 */
public abstract class WebAccessSystemTest extends WebTest {

    protected RemoteWebDriver webDriver;

    @Test
    @Category(WebSystemTests.class)
    public void unknownFacility() throws Exception {

        //Sets the webdriver to be used
        webDriver.get(getPath());
        //Sets the amount to wait before throwing an exception for finding an element
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        //Allows access to the email, password fields and the login button
        WebElement emailElement = webDriver.findElementById("Email");
        WebElement passwordElement = webDriver.findElementById("Password");
        List<WebElement> buttonList = webDriver.findElements(By.className("w-button"));

        /*
        Test 1 Log-In
        Email - valid
        Password - invalid
         */

        System.out.println("Test 1 - \n Sign In \n Email - valid: user@test.com \n Password - invalid: password1");

        //Sets the email field to user@test.com
        emailElement.sendKeys("user@test.com");

        //Sets the password field to password1
        passwordElement.sendKeys("password1");

        //Clicks the login buttin
        buttonList.get(0).click();

        wait.until(ExpectedConditions.textToBePresentInElement(webDriver.findElementById("login-error"), "The password is invalid or the user does not have a password."));
        assert (webDriver.getPageSource().contains("The password is invalid or the user does not have a password."));

        emailElement.clear();
        passwordElement.clear();

        /*
        Test 2 Log-In
        Email - valid not registered
        Password - anything
         */

        System.out.println("Test 2 - \n Sign In \n Email - valid not registered: email@test.com \n Password - anything: password1");

        //Sets the email field to email@test.com
        emailElement.sendKeys("email@test.com");

        //Sets the password field to password1
        passwordElement.sendKeys("password1");

        //Clicks the login buttin
        buttonList.get(0).click();

        wait.until(ExpectedConditions.textToBePresentInElement(webDriver.findElementById("login-error"),"There is no user record corresponding to this identifier. The user may have been deleted."));
        assert(webDriver.getPageSource().contains("There is no user record corresponding to this identifier. The user may have been deleted."));

        emailElement.clear();
        passwordElement.clear();

        /*
        Test 3 Log-In Navigation
        */

        System.out.println("Test 3 - \n Log-In Navigation \n Go to Reset Password Page then New Administrator page");

        List<WebElement> logInTabs = webDriver.findElements(By.className("access-tab"));
        //Go to new Administrator page
        logInTabs.get(1).click();

        //Registers the Confirm Password field to ensure on correct page
        WebElement confirmPassword = webDriver.findElementById("Register-Confirm-Password");
        assert(confirmPassword!=null);

        //Go to new Reset Password page
        logInTabs.get(2).click();

        //Registers the Confirm Password field to ensure on correct page
        wait.until(ExpectedConditions.elementToBeClickable(buttonList.get(2)));
        assert(buttonList.get(2).isDisplayed());

        /*
        Test 4 Log-In new administrator
        */


        System.out.println("Test 4 - \n New Administrator \n Already registered \n Email - valid: email@test.com \n Password - anything: password1 \n Confirm Password - anything: password1");
        //Go to new Administrator page
        logInTabs.get(1).click();

        WebElement registerElement = webDriver.findElementById("Register-Email");
        WebElement regPasswordElement = webDriver.findElementById("Register-Password");
        WebElement conPasswordElement = webDriver.findElementById("Register-Confirm-Password");

        //Sets the email field to email@test.com
        registerElement.sendKeys("user@test.com");

        //Sets the password field to password1
        regPasswordElement.sendKeys("password1");

        //Sets the confirm password field to password1
        conPasswordElement.sendKeys("password1");

        //Clicks the register button
        buttonList.get(1).click();

        wait.until(ExpectedConditions.textToBePresentInElement(webDriver.findElementById("register-error"),"The email address is already in use by another account."));
        assert(webDriver.getPageSource().contains("The email address is already in use by another account."));

        registerElement.clear();
        regPasswordElement.clear();
        conPasswordElement.clear();


         /*
        Test 5 Log-In Sign-Out Log-In
        Email - valid
        Password - valid
         */
        System.out.println("Test 5 - \n Sign In Sign Out Navigation \n Sign In using existing account, Sign Out from homepage, Sign In again");

        //Go to Sign In page
        logInTabs.get(0).click();

        //Sets the email field to personal.allankerr@gmail.com
        emailElement.sendKeys("personal.allankerr@gmail.com");

        //Sets the password field to password1
        passwordElement.sendKeys("password");

        //Clicks the login buttin
        System.out.println("Signing In....");
        buttonList.get(0).click();

        //Checks that the page has been correctly changed
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("logo-title")));
        assert(webDriver.findElement(By.className("logo-title")).isDisplayed());


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[1]/a")));
        WebElement signOut = webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/a"));

        //Clicks Sign Out Button
        System.out.println("Signing Out....");
        System.out.println(signOut.getText());
        TimeUnit.SECONDS.sleep(2);
        signOut.click();

        //Checks that the page has been correctly changed
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"Email\"]")));
        assert((webDriver.findElement(By.className("access-title")).getText().contains("LTC Safety")));

        WebElement emailElement1 = webDriver.findElementById("Email");
        WebElement passwordElement1 = webDriver.findElementById("Password");

        //Sets the email field to personal.allankerr@gmail.com
        emailElement1.sendKeys("personal.allankerr@gmail.com");

        //Sets the password field to password1
        passwordElement1.sendKeys("password");


        List<WebElement> buttonList1 = webDriver.findElements(By.className("w-button"));

        //Clicks the login buttin
        System.out.println("Signing In....");
        buttonList1.get(0).click();

        //Checks that the page has been correctly changed
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("logo-title")));
        assert(webDriver.findElement(By.className("logo-title")).isDisplayed());




         /*
        Test 6
        Test the left, right navigation arrows and the page refresh
         */

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/a[2]")));
        //Access the right click arrow
        WebElement rightArrow = webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/a[2]"));
        System.out.println("Navigating right to older concerns");
        rightArrow.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]")));
        assert(webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/div")).getText().contains("26 - 50"));

        //Access the left click arrow
        WebElement leftArrow = webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/a[3]"));
        System.out.println("Navigating left to newer concerns");
        leftArrow.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]")));
        assert(webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/div")).getText().contains("1 - 25"));

        //Access the refresh button
        WebElement refreshButton = webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/a[1]"));
        System.out.println("Clicking Refresh to update concerns");
        refreshButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]")));
        assert(webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]")).isDisplayed());



         /*
        Test 7
        Test the concern is viewable with the correct information
         */

        System.out.println("Test 7 - \n Concern Navigation \n View a specific concern \n Check its contains the correct info");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]")));
        WebElement testConcern = webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]"));
        String testConFacility = webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]/div[2]")).getText();
        String testConNature = webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]/div[1]")).getText();
        String testConSubDate = webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/ul/li[1]/div[4]")).getText();

        //Clicking the top concern on the page
        System.out.println("viewing the top concern... ");
        testConcern.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div/div[2]/a")));
        System.out.println("Checking the concern nature matches, " + testConNature);
        assert(webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div/div[1]/div[1]/ul/li[1]/div/div[2]")).getText().contains(testConNature));
        System.out.println("Checking the concern facility matches, " + testConFacility);
        assert(webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div/div[1]/div[2]/ul[1]/li[1]/div/div[2]")).getText().contains(testConFacility));
        System.out.println("Checking the concern sub date matches, " + testConSubDate);
        assert(webDriver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div/div[1]/div[1]/ul/li[2]/div/div[2]")).getText().contains(testConSubDate));




    }

    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.close();
        }
    }
}
