package c371g2.ltc_safety.local;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import c371g2.ltc_safety.R;

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
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy, h:mm a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, h:mm a");
        return simpleDateFormat.format(date);
    }

    public String getType() {
        return type;
    }

    public int getReadableTypeResource() throws Exception {
        switch(type) {
            case "PENDING":
                return R.string.PENDING_text;
            case "SEEN":
                 return R.string.SEEN_text;
            case "RESPONDING24":
                return R.string.RESPONDING24_text;
            case "RESPONDING48":
                return R.string.RESPONDING48_text;
            case "RESPONDING72":
                return R.string.RESPONDING72_text;
            case "RESOLVED":
                return R.string.RESOLVED_text;
            case "RETRACTED":
                return R.string.RETRACTED_text;
            default:
                throw new Exception("Unknown status type");
        }
    }
}
