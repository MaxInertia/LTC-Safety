package c371g2.ltc_safety;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public abstract class AbstractNetworkViewModel {
    /**
     * Reference to the Activity class which initialized this class.
     */
    public AbstractNetworkActivity activity;
    /**
     * A class used to inform a test class that a network operation has been completed.
     * This variable is null until a concern submission is attempted with valid inputs.
     */
    public final CountDownLatch signalLatch = new CountDownLatch(1);
    /**
     * The return code that results from an attempt to submit a concern.
     * This variable is null until a concern submission is attempted.
     */
    public ReturnCode submissionReturnCode;
}
