package c371g2.ltc_safety.a_new;

import android.support.annotation.NonNull;

import c371g2.ltc_safety.AbstractNetworkActivity;

/**
 * Interface that grants access to the static class Test_Hook inside the NewConcernViewModel class.
 *
 * If necessary: Place headers for test methods here, implement those methods in Test_Hook.
 *
 * Note: This is intended to grant access to non-public fields and methods from outside the a_main
 * package. If this is not required, write tests in either the Test or AndroidTest directory in the
 * same package as the class being tested.
 *
 * Test Directory: 'Android\LTC-Safety\app\src\test\java\c371g2\ltc_safety'
 * AndroidTest Directory: 'Android\LTC-Safety\app\src\androidTest\java\c371g2\ltc_safety'
 *
 * @Invariants instance is always non-null.
 * @HistoryProperties none.
 */
public interface NewConcernViewModel_TestHook {
    /**
     * The instance used to access Test_Hook, an inner-Class of NewConcernViewModel.
     *
     * NewConcernViewModel_TestHook: This interface.
     * NewConcernViewModel.Test_Hook: Static inner-Class of NewConcernViewModel
     */
    NewConcernViewModel_TestHook instance = new NewConcernViewModel.Test_Hook();

    /**
     * Submit concern with the provided fields.
     * @preconditions testActivity is non-null and is running.
     * @modifies The submitted concern, if successful, is added to MainViewModel.concernList
     * @param testActivity activity that implements AbstractNetworkActivity
     * @param concernType The Concern nature
     * @param actionsTaken The actions taken
     * @param facilityName The name of the long term care facility
     * @param roomName A room name
     * @param reporterName First and last name of the reporter
     * @param emailAddress Reporter email address
     * @param phoneNumber Reporter phone number
     * @return True if the concern was successfully submitted, otherwise false.
     * @throws InterruptedException
     */
    boolean submitConcern(@NonNull AbstractNetworkActivity testActivity, String concernType, String actionsTaken, String facilityName,
                          String roomName, String reporterName, String emailAddress, String phoneNumber) throws InterruptedException;

}
