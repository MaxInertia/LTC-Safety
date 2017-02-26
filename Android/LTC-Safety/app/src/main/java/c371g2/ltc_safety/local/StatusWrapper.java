package c371g2.ltc_safety.local;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used to store the details of ConcernStatus objects locally.
 */
public class StatusWrapper {
    private String type;
    private long date;

    StatusWrapper(String t, long d) {
        type = t;
        date = d;
    }

    public String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy, h:mm a");
        return simpleDateFormat.format(date);
    }

    public String getType() {
        return type;
    }
}
