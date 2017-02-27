package c371g2.ltc_safety.a_new;

import android.app.Activity;

/**
 * Interface that grants access to the static subclass TestHook inside the NewConcernViewModel class.
 *
 * If necessary: Place headers for test methods here, implement those methods in TestHook.
 *
 * Note: This is intended to grant access to non-public fields and methods from outside the a_main
 * package. If this is not required, write tests in either the Test or AndroidTest directory in the
 * same package as the class being tested.
 *
 * Test Directory: 'Android\LTC-Safety\app\src\test\java\c371g2\ltc_safety'
 * AndroidTest Directory: 'Android\LTC-Safety\app\src\androidTest\java\c371g2\ltc_safety'
 */
public interface NewConcernViewModel_TestHook {

    NewConcernViewModel_TestHook instance = new NewConcernViewModel.Test_Hook();

    /**
     * Submit concern
     * @param testActivity activity that implements BasicActivity
     * @param concernType ''
     * @param actionsTaken ''
     * @param facilityName ''
     * @param roomName ''
     * @param reporterName ''
     * @param emailAddress ''
     * @param phoneNumber ''
     * @return True if the concern was successfully submitted, otherwise false.
     * @throws InterruptedException
     */
    boolean submitConcern(Activity testActivity, String concernType, String actionsTaken, String facilityName,
                              String roomName, String reporterName, String emailAddress, String phoneNumber) throws InterruptedException;

}
