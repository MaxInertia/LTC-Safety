package c371g2.ltc_safety.a_main;

/**
 * Return codes for the concern fetching network operation.
 */
public enum FetchReturnCode {
      /**
     * The API threw an IOException.
     */
    IOEXCEPTION_THROWN_BY_API(1),

    /**
     * Concern fetching was successful.
     */
    SUCCESS(2),

    /**
     * Update attempted, but no concerns are stored on this device.
     */
    NO_CONCERNS(3);

    public int id;
    FetchReturnCode(int id) {
        this.id = id;
    }
}
