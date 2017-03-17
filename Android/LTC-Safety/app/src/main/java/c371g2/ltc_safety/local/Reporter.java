package c371g2.ltc_safety.local;

import java.io.Serializable;

/**
 * This class is used to store data on the reporter of concerns.
 * NOT TO BE CONFUSED WITH CLIENT-API Reporter
 * @Invariants
 * - No fields are null
 * - Of the two fields emailAddress and phoneNumber, only one can be an empty string.
 * @HistoryProperties All fields are final, they always contain the same instance.
 */
public class Reporter implements Serializable {

    /**
     * The first and last name of the reporter.
     */
    private final String name;
    /**
     * The reporters email address.
     */
    private final String emailAddress;
    /**
     * The reporters phone number.
     */
    private final String phoneNumber;

    public Reporter(String name, String emailAddress, String phoneNumber){
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retrieves the name of the reporters.
     * @preconditions none.
     * @modifies nothing.
     * @return Reporter name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the reporters email address, if provided.
     * @preconditions none.
     * @modifies nothing.
     * @return Reporter email address or empty string.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Retrieves the reporters phone number, if provided.
     * @preconditions none.
     * @modifies nothing.
     * @return Reporter phone number or empty string.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

}
