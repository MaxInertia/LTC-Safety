package c371g2.ltc_safety.a_detail;

/**
 * Interface that grants access to the static class Test_Hook inside the ConcernDetailViewModel
 * class.
 *
 * If necessary: Place headers for test methods here, implement those methods in Test_Hook.
 *
 * Note: This is intended to grant access to package-private fields and methods from outside the
 * a_main package. If this is not required, write tests in either the Test or AndroidTest directory
 * in the same package as the class being tested.
 *
 * Test Directory: 'Android\LTC-Safety\app\src\test\java\c371g2\ltc_safety'
 * AndroidTest Directory: 'Android\LTC-Safety\app\src\androidTest\java\c371g2\ltc_safety'
 *
 * @Invariants none.
 * @HistoryProperties none.
 */
public interface ConcernDetailViewModel_TestHook {
    /**
     * The instance used to access Test_Hook, an inner-Class of ConcernDetailViewModel.
     *
     * ConcernDetailViewModel_TestHook: This interface.
     * ConcernDetailViewModel.Test_Hook: Static inner-Class of ConcernDetailViewModel.
     */
    ConcernDetailViewModel_TestHook instance = new ConcernDetailViewModel.Test_Hook();
}
