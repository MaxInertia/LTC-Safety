package c371g2.ltc_safety.a_main;

import java.util.ArrayList;

import c371g2.ltc_safety.local.Concern;

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
    void newConcernSubmitted(Concern concern);
}
