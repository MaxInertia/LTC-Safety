package c371g2.ltc_safety.a_detail;

/**
 * Return codes used in ConcernDetailViewModel when retracting a concern.
 */
public enum ReturnCode {
    /**
     * Client-API threw an IOException when attempting to retract a concern.
     */
    IOEXCEPTION,
    /**
     * No exception was thrown when attempting to retract a concern.
     */
    NOERROR,
    /**
     * The concern was successfully retracted.
     */
    SUCCESS
}
