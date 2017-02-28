package c371g2.ltc_safety;

import java.util.concurrent.CountDownLatch;

/**
 * The abstract class extended by each ViewModel that performs a network operation.
 * NewConcernViewModel: Concern submission.
 * ConcernDetailViewModel: Concern retraction.
 *
 * @Invariants signalLatch is always non-null.
 * @HistoryProperties signalLatch.value() only decreases but is never negative.
 */
public abstract class AbstractNetworkViewModel {
    /**
     * Reference to the Activity class which initialized this class.
     */
    public AbstractNetworkActivity activity;
    /**
     * A class used to inform a test class that a network operation has been completed.
     */
    public final CountDownLatch signalLatch = new CountDownLatch(1);
    /**
     * The return code that results from an attempt to submit or retract a concern.
     * This variable is null until a concern submission or retraction is attempted.
     */
    public ReturnCode submissionReturnCode;
}
