package c371g2.ltc_safety.local;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Tests the methods in the Reporter class.
 */
public class ReporterTests {

    @Test
    public void test_getName() {
        String testName = "Jessica Jones";
        Reporter reporter = new Reporter(testName,null,null);

        assertTrue(reporter.getName().equals(testName));
    }

    @Test
    public void test_getPhoneNumber() {
        String testNumber = "1231231234";
        Reporter reporter = new Reporter(null,null,testNumber);

        assertTrue(reporter.getPhoneNumber().equals(testNumber));
    }

    @Test
    public void test_getEmailAddress() {
        String email = "Valid.Email.Address@Form.com";
        Reporter reporter = new Reporter(null,email,null);

        assertTrue(reporter.getEmailAddress().equals(email));
    }

}
