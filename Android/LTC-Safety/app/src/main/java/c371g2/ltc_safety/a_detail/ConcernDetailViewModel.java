package c371g2.ltc_safety.a_detail;

import android.app.Activity;

import java.util.concurrent.CountDownLatch;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This class acts as an interface between the app view in the concern detail activity and the model.
 * @Invariants none
 * @HistoryProperties none
 */
class ConcernDetailViewModel {

    private ConcernDetailActivity activity;
    final CountDownLatch signalLatch;

    private ConcernWrapper concern;

    ConcernDetailViewModel(ConcernDetailActivity activity) {
        signalLatch = new CountDownLatch(1);
        this.activity = activity;
    }

    /**
     * Retrieve concern from MainViewModel
     * @param index index of requested concern
     * @return concern at specified index
     */
    ConcernWrapper getConcern(int index) {
        return null;
    }

    /**
     * Retract the concern currently being viewed on the ConcernDetailActivity
     */
    protected void retractConcern() {
        //TODO: Use client-API here to retract the concern currently being viewed
    }

}
