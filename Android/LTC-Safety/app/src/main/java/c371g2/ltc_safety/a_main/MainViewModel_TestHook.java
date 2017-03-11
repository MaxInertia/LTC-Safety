package c371g2.ltc_safety.a_main;

import c371g2.ltc_safety.AbstractNetworkActivity;
import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * Interface that grants access to the static class Test_Hook inside the MainViewModel class.
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
public interface MainViewModel_TestHook {
    /**
     * The instance used to access Test_Hook, an inner-Class of MainViewModel.
     *
     * MainViewModel_TestHook: This interface.
     * MainViewModel.Test_Hook: Static inner-Class of MainViewModel.
     */
    MainViewModel_TestHook instance = new MainViewModel.Test_Hook();

    /**
     * Clears the list of concerns in MainViewModel.
     * @preconditions none.
     * @modifies MainViewModel.concernList is reinitialized.
     */
    void clearConcernList();

    /**
     * Adds a concern to the list of concerns in MainViewModel.
     * @preconditions Either clearConcernList() or MainViewModel.initialize() has been called at
     * some point during the current runtime. If this is not the case, the concernList will be null
     * and a NullPointerException will be thrown.
     * to this method being called.
     * @param concern The concern to be added.
     * @throws NullPointerException If MainViewModel.concernList was not initialized prior to calling
     */
    void addConcern(ConcernWrapper concern) throws NullPointerException;
}
