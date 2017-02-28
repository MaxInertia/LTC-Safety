package c371g2.ltc_safety;

/**
 * Return codes for concern submission and retraction
 */
public enum ReturnCode {
    /**
     * The user attempted submitting a concern without a concern type selected
     */
    NO_CONCERN_TYPE(1),

    /**
     * The user attempted submitting a concern without a facility selected
     */
    NO_FACILITY_NAME(2),

    /**
     * The user attempted submitting a concern without providing their name
     */
    INVALID_NAME(3),

    /**
     * The user attempted submitting a concern without providing any of their contact information
     */
    INVALID_PHONE_AND_EMAIL(4),

    /**
     * The user is attempting to submit a concern with what appears to be valid inputs
     */
    VALID_INPUT(5),

    /**
     * The API threw an IOException
     */
    IOEXCEPTION_THROWN_BY_API(6),

    /**
     * Concern submission/retraction was successful
     */
    SUCCESS(7);

    public int id;
    ReturnCode(int id) {
        this.id = id;
    }
}
