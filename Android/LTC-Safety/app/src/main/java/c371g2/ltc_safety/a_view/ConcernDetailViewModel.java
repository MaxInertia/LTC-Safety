package c371g2.ltc_safety.a_view;

/**
 * This class acts as an interface between the app view in the concern detail activity and the model.
 * @Invariants none
 * @HistoryProperties none
 */
public class ConcernDetailViewModel {

    String ownerToken;

    /**
     * Retract the concern currently being viewed on the ConcernDetailActivity
     */
    protected void retractConcern() {
        //TODO: Use client-API here to retract the concern currently being viewed
    }

}
