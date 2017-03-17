package c371g2.ltc_safety;

import java.util.concurrent.CountDownLatch;

/**
 * The abstract class extended by each ViewModel that performs a network operation.
 * MainViewModel: Concern fetching (updating statuses).
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
     * Used to inform a test class that a network operation has been completed.
     */
    public final CountDownLatch signalLatch = new CountDownLatch(1);
}
