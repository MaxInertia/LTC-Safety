package c371g2.ltc_safety.local;

import android.os.Bundle;

import com.appspot.ltc_safety.client.model.OwnerToken;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is used to store the details of submitted Concerns on the reporters device along with
 * the owner token associated with that concern. It provides the data required to:
 * 1. Populate the concern list on the main activity,
 * 2. Fill in the fields on the view concern activity,
 * 3. Retract the concern.
 * NOT TO BE CONFUSED WITH CLIENT-API Concern
 * @Invariants none
 * @HistoryProperties All fields except token are final.
 */
public class Concern {

    private com.appspot.ltc_safety.client.model.Concern data;
    private OwnerToken ownerToken;

    public Concern(com.appspot.ltc_safety.client.model.Concern clientConcern, OwnerToken ownerToken) {
        data = clientConcern;
        this.ownerToken = ownerToken;
    }

    public com.appspot.ltc_safety.client.model.Concern getConcernObject() {
        return data;
    }

    public OwnerToken getOwnerToken() {
        return ownerToken;
    }

    /**
     * Create a bundle containing this concern. Used for transferring concerns between activities.
     * @preconditions This concern object contains all fields required to be submitted: Reporter name,
     * one contact method (email or phone), concern type, and facility. This concern must also have
     * a valid owner token.
     * @modifies Nothing, this concern is unchanged.
     * @return a bundle containing all of this concerns data.
     */
    public Bundle toBundle() {
        return new Bundle();
    }


}
