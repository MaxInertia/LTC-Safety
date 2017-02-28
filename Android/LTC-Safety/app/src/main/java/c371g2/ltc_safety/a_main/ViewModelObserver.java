package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.support.annotation.NonNull;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This interface is implemented by MainViewModel and used by NewConcernViewModel and ConcernDetailViewModel.
 * - NewConcernViewModel uses this interface to add a newly submitted concern to the list of concerns
 * stored on the device.
 * - ConcernDetailViewModel uses this interface to (1) retrieve specific concerns from
 * MainViewModel.concernList and to (2) update concerns statuses when they are retracted.
 *
 * @Invariants instance is always initialized.
 * @HistoryProperties none.
 */
public interface ViewModelObserver {
    ViewModelObserver instance = new MainViewModel();

    /**
     * Only by NewConcernViewModel to inform MainViewModel that a new concern has been submitted.
     * The new concern is added to the list of concerns in MainViewModel.
     * @preconditions concern is not null.
     * @modifies concernList in MainViewModel; concern is added to the list.
     * @param context The context of the NewConcernActivity instance calling this method.
     * @param concern The newly submitted concern.
     */
    void newConcernSubmitted(@NonNull Context context,@NonNull ConcernWrapper concern);

    /**
     * Used only by ConcernDetailViewModel. Informs the MainViewModel of a request to see the
     * detailed view of a concern. The concern at the specified index of the concernList is returned
     * to assist in fulfilling this request.
     * @preconditions
     * - MainViewModel.concernList is not null.
     * - index is in the range: [ 0, MainViewModel.concernList.size()-1 ]
     * @modifies nothing.
     * @param index the index of the requested concern
     * @return The concern at the specified index
     */
    ConcernWrapper getConcernAtIndex(int index);

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
     * @param index The index of the retracted concern in concernList
     */
    void concernRetracted(@NonNull Context context,@NonNull ConcernWrapper concern, int index);
}
