package c371g2.ltc_safety.local;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Used to store the details of ConcernStatus objects locally.
 * @Invariants
 * - All fields are non-null
 * - The fields are final, they always contain the same instance of their class.
 */
public class StatusWrapper implements Serializable {
    final private String type;
    final private long date;

    public StatusWrapper(String type, long date) {
        this.type = type;
        this.date = date;
    }

    public String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy, h:mm a");
        return simpleDateFormat.format(date);
    }

    public String getType() {
        return type;
    }
}
