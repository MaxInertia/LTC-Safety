package com.cs371group2.concern;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The reporter class is used to store information pertaining to the reporter of the concern. This
 * consists of name and contact information.
 *
 * History properties: Name, phone number, and email are all guaranteed to be constant during the
 * entire duration of a reporter instance.
 *
 * Invariance properties: This class makes no assumptions about the information it is given, though to be considered
 * "valid" in our system, it is required that all fields are non-null.
 *
 * Created on 2017-01-17.
 */
public final class Reporter implements Validatable {

    private static final Logger logger = Logger.getLogger(Concern.class.getName());

    private static final String REPORTER_CONTACT_ERROR = "Either a phone number or an email address must be provided when submitting a concern.";
    private static final String REPORTER_NAME_ERROR = "A first and last name must be provided when submitting a concern.";

    /**
     * The name of the reporter of a concern. This will generally be the first and last name of the
     * reporter but may vary as the input format is not enforced. Name must not be null.
     */
    private String name;

    /**
     * The phone number used to contact the reporter. The format of this phone number may vary as
     * the input format is not enforced. The phone number may be null only if the email address
     * isn't.
     */
    private String phoneNumber;

    /**
     * The email address used to contact the reporter. The format of this phone number may vary as
     * the input is only checked for basic email formatting in the form of address@domain. The email
     * address may be null only if the phone number isn't.
     */
    private String email;

    /**
     * TestHook_MutableReporter is a test hook to make Reporter testable without exposing its
     * members. An instance of TestHook_MutableReporter can be used to construct new Reporters and
     * set values for testing purposes.
     */
    public static class TestHook_MutableReporter {

        /**
         * The reporter data that is being built.
         */
        private Reporter reporter = new Reporter();

        /**
         * Constructs a new mutable reporter.
         *
         * @param name The name of the reporter.
         * @param email The email of the reporter.
         * @param phoneNumber The phone number of the rpoerter.
         */
        public TestHook_MutableReporter(String name, String email, String phoneNumber) {
            this.reporter.name = name;
            this.reporter.email = email;
            this.reporter.phoneNumber = phoneNumber;
        }

        public void setName(String name) {
            this.reporter.name = name;
        }

        public void setEmail(String email) {
            this.reporter.email = email;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.reporter.phoneNumber = phoneNumber;
        }

        /**
         * Converts the mutable reporter to a reporter instance to be used for testing. Once built
         * the reporter is immutable regardless of whether the mutable reporter it was created with
         * is modified.
         *
         * @return The immutable reporter reference containing the mutable reporter's data.
         */
        public Reporter build() {
            Reporter immutable = new Reporter();
            immutable.name = this.reporter.name;
            immutable.email = this.reporter.email;
            immutable.phoneNumber = this.reporter.phoneNumber;
            return immutable;
        }
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public ValidationResult validate() {
        if (name == null) {
            logger.log(Level.WARNING,
                    "Validation of Concern Reporter failed due to null reporter name.");
            return new ValidationResult(REPORTER_NAME_ERROR);
        }
        if (phoneNumber == null && email == null) {
            logger.log(Level.WARNING,
                    "Validation of Concern Reporter failed due to null phone number and email.");
            return new ValidationResult(REPORTER_CONTACT_ERROR);
        }
        logger.log(Level.FINER, "Validation of Concern Reporter successful.");
        return new ValidationResult();
    }

    @Override
    public String toString() {
        return "Reporter Name:  " + this.getName()
                + "\nReporter Phone Number: " + this.getPhoneNumber()
                + "\nReporter Email: " + this.getEmail() + "\n";
    }
}
