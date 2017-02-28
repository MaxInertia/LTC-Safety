package c371g2.ltc_safety.local;

import java.text.SimpleDateFormat;

/**
 * Used to store the details of ConcernStatus objects locally.
 * @Invariants
 * - All fields are non-null
 * - The fields are final, they always contain the same instance of their class.
 */
public class StatusWrapper {
    final private String type;
    final private long date;

    public StatusWrapper(String t, long d) {
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
