package c371g2.ltc_safety.a_main;

import android.content.Context;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 *
 */
public interface ViewModelObserver {
    ViewModelObserver instance = new MainViewModel();

    /**
     * Used only by NewConcernViewModel to inform MainViewModel that a new concern has been submitted.
     * The new concern is added to the list of concerns in MainViewModel.
     * @param concern The last concern submitted.
     * @preconditions concern is not null.
     * @modifies concernList in MainViewModel; concern is added to the list.
     */
    void newConcernSubmitted(Context context, ConcernWrapper concern);

    /**
     * Used only by ConcernDetailViewModel. Informs the MainViewModel of a request to see the
     * detailed view of a concern. The concern at the specified index of the concernList is returned
     * to assist in fulfilling this request.
     * @param index the index of the requested concern
     * @return The concern at the specified index
     */
    ConcernWrapper getConcernAtIndex(int index);

    /**
     * Used only by ConcernDetailViewModel. Informs the MainViewModel that a concern has been
     * retracted so the concern can be updated.
     * @param context The context of the ConcernDetailActivity
     * @param concern The concern which was retracted
     * @param index The index of the retracted concern in concernList
     */
    void concernRetracted(Context context, ConcernWrapper concern, int index);
}
