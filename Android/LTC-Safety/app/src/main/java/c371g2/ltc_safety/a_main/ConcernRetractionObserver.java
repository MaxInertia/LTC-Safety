package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.Serializable;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This interface is implemented by MainViewModel and used by ConcernDetailViewModel to update a
 * given concerns statuses when it has been retracted.
 * @Invariants none.
 * @HistoryProperties none.
 */
public interface ConcernRetractionObserver extends Serializable {

    /**
     * Used only by ConcernDetailViewModel. Informs the MainViewModel that a concern has been
     * retracted so the concern can be updated.
     * @preconditions
     * - context and concern are not null
     * - index is in the range: [ 0, MainViewModel.concernList.size()-1 ]
     * @modifies
     * - The concern at index 'index' is replaced with 'concern' (The index and concern in single
     * quotes refers to the parameters of this method).
     * @param context The context of the ConcernDetailActivity
     * @param concern The concern which was retracted
     */
    void concernRetracted(@NonNull Context context,@NonNull ConcernWrapper concern);
}
