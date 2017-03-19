package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.Serializable;

import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This interface is implemented by MainViewModel and used by NewConcernViewModel to inform the
 * MainViewModel that a concern has been submitted. The newly submitted concern is then added to
 * the list of concerns stored on the device.
 * @Invariants none.
 * @HistoryProperties none.
 */
public interface ConcernSubmissionObserver extends Serializable{

    /**
     * Only by NewConcernViewModel to inform MainViewModel that a new concern has been submitted.
     * The new concern is added to the list of concerns in MainViewModel.
     * @preconditions concern is not null.
     * @modifies concernList in MainViewModel; concern is added to the list.
     * @param context The context of the NewConcernActivity instance calling this method.
     * @param concern The newly submitted concern.
     */
    void concernSubmitted(@NonNull Context context, @NonNull ConcernWrapper concern);

}
