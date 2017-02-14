package c371g2.ltc_safety.a_new;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * This class is used to test methods in the NewConcernViewModel class. This test class requires a
 * device (real or emulated) to run.
 */
@RunWith(AndroidJUnit4.class)
public class NewConcernTests {

    @Test
    public void test_ConcernSubmission_allFields() {
        String concernType = "Equipment failure";
        String actionsTaken = "Stopped wearing nerve-gear";
        String facultyName = "Luthercare LTC";
        String reporterName = "Kayaba Akihiko";
        String emailAddress = "Kayaki@Aincrad.net";
        String phoneNumber = "3063063066";
        try{
            NewConcernViewModel.submitConcern(concernType,actionsTaken,facultyName,reporterName,emailAddress,phoneNumber);

        } catch (IOException ioException) {
            ioException.printStackTrace();
            fail("NewConcernViewModel.submitConcern(...) threw an IOException.");
        }
    }

    @Test
    public void test_ConcernSubmission_NoEmail() {
        String concernType = "Injury";
        String actionsTaken = "Ate lots of pumpkin pie";
        String facultyName = "Luthercare LTC";
        String reporterName = "Ryuzaki L";
        String emailAddress = "";
        String phoneNumber = "3063063066";
        try{
            NewConcernViewModel.submitConcern(concernType,actionsTaken,facultyName,reporterName,emailAddress,phoneNumber);

        } catch (IOException ioException) {
            ioException.printStackTrace();
            fail("NewConcernViewModel.submitConcern(...) threw an IOException.");
        }
    }

    @Test
    public void test_ConcernSubmission_NoPhone() {
        String concernType = "Medication problem";
        String actionsTaken = "Had a picnic";
        String facultyName = "Luthercare LTC";
        String reporterName = "Ichigo Kurosaki";
        String emailAddress = "IchiKuro@soulsociety.com";
        String phoneNumber = "";
        try{
            NewConcernViewModel.submitConcern(concernType,actionsTaken,facultyName,reporterName,emailAddress,phoneNumber);

        } catch (IOException ioException) {
            ioException.printStackTrace();
            fail("NewConcernViewModel.submitConcern(...) threw an IOException.");
        }
    }
}
