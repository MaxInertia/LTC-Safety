package c371g2.ltc_safety;

import java.lang.ref.WeakReference;
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
    transient public WeakReference<AbstractNetworkActivity> activityWeakReference;
    /**
     * Used to inform a test class that a network operation has been completed.
     */
    transient public final CountDownLatch signalLatch = new CountDownLatch(1);

    /**
     * Called in the onDestroy method of an AbstractNetworkActivity to end any threads running prior
     * to destroying the activity that thread was created in.
     */
    protected abstract void stopNetworkThread();
}
