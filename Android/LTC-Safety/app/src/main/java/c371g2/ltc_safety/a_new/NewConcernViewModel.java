package c371g2.ltc_safety.a_new;

/**
 * This class acts as an interface between the app view in the new concern activity and the model.
 * @Invariants none
 * @HistoryProperties none
 */
class NewConcernViewModel {

    /**
     * Generates and submits a concern with the provided details to the Google Endpoints Backend.
     * @preconditions none
     * @modifies If the input is valid: A new Concern is added to the list of concerns, and  the App
     *  view changes to the ConcernDetailActivity (or MainActivity?). Validity of input is determined
     *  by the classes that extend Verifier.
     * @param concernType The type of concern being submitted.
     * @param actionsTaken The actions taken for the concern.
     * @param facultyName The facility the concern is about.
     * @param reporterName The first and last name of the reporter.
     * @param emailAddress An email address the reporter can be contacted at.
     * @param phoneNumber A phone number that the reporter can be contacted at.
     */
    static void submitConcern(String concernType, String actionsTaken, String facultyName,
                                        String reporterName, String emailAddress, String phoneNumber) {
        // TODO: Use verifier-classes to confirm input is sufficient to submit as concern
        // TODO: Use 'client' API here to generate and submit concern
    }
}
