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
}
