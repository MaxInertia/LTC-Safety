package c371g2.ltc_safety.a_detail;

/**
 * Return codes for the concern retraction network operation.
 */
public enum RetractionReturnCode {
    /**
     * The API threw an IOException.
     */
    IOEXCEPTION_THROWN_BY_API(1),

    /**
     * Concern retraction was successful.
     */
    SUCCESS(2);

    public int id;
    RetractionReturnCode(int id) {
        this.id = id;
    }
}
