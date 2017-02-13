package c371g2.ltc_safety.local;

/**
 * This class is used to store data on the reporter of concerns.
 * NOT TO BE CONFUSED WITH CLIENT-API Reporter
 * @Invariants none
 * @HistoryProperties All fields are final.
 */
public class Reporter {

    private final String name;
    private final String emailAddress;
    private final String phoneNumber;

    public Reporter(String name, String emailAddress, String phoneNumber){
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
