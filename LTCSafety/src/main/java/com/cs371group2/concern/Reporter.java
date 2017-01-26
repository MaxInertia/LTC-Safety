package com.cs371group2.concern;

import com.cs371group2.Validatable;
import com.cs371group2.ValidationResult;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The reporter class is used to store information pertaining to the reporter of the concern. This
 * consists of name and contact information.
 *
 * Created on 2017-01-17.
 */
public final class Reporter implements Validatable {
    /**
     * Logger definition for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Concern.class.getName() );

    private static final String REPORTER_CONTACT_ERROR = "Either a phone number or an email address must be provided when submitting a concern.";
    private static final String REPORTER_NAME_ERROR = "A first and last name must be provided when submitting a concern.";

    /**
     * The name of the reporter of a concern. This will generally be the first and last name of the
     * reporter but may vary as the input format is not enforced. Name must not be null.
     */
    String name;

    /**
     * The phone number used to contact the reporter. The format of this phone number may vary as
     * the input format is not enforced. The phone number may be null only if the email address
     * isn't.
     */
    String phoneNumber;

    /**
     * The email address used to contact the reporter. The format of this phone number may vary as
     * the input is only checked for basic email formatting in the form of address@domain. The email
     * address may be null only if the phone number isn't.
     */
    String email;

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
            LOGGER.log(Level.FINE, "Validation of Concern Reporter failed due to null reporter name.");
            return new ValidationResult(REPORTER_NAME_ERROR);
        }
        if (phoneNumber == null && email == null) {
            LOGGER.log(Level.FINE, "Validation of Concern Reporter failed due to null phone number and email.");
            return new ValidationResult(REPORTER_CONTACT_ERROR);
        }
        LOGGER.log(Level.FINER, "Validation of Concern Reporter successful.");
        return new ValidationResult();
    }

    @Override
    public String toString(){
        return "Reporter Name:  " + this.getName()
                + "\nReporter Phone Number: " + this.getPhoneNumber()
                + "\nReporter Email: " + this.getEmail() + "\n";
    }
}
