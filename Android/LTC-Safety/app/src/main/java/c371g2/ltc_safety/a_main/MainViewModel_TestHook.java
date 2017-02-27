package c371g2.ltc_safety.a_main;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * Interface that grants access to the static subclass TestHook inside the MainViewModel class.
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
public interface MainViewModel_TestHook {
    MainViewModel_TestHook instance = new MainViewModel.Test_Hook();

    void clearConcernList();
    void addConcern(ConcernWrapper concern);
}
