package c371g2.ltc_safety;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

/**
 * The abstract class extended by each activity whose ViewModel performs a network operation.
 * NewConcernActivity's ViewModel: Concern submission.
 * ConcernDetailActivity's ViewModel: Concern retraction.
 *
 * @Invariants none
 * @HistoryProperties none
 */
public abstract class AbstractNetworkActivity extends AppCompatActivity {
    /**
     * A popup that is used to inform the user that the network operation requested is in progress.
     * This variable is null until a concern is retracted or a concern submission is attempted with
     * valid inputs.
     */
    public AlertDialog progressDialog;

    /**
     * Display a popup. This can contain a title and/or a message. A listener can be linked to the
     * popup; the operation in the listener will be performed when the popup is dismissed.
     * @preconditions none
     * @modifies nothing
     * @param title Title of the popup
     * @param message Text in the popup
     * @param listener The listener for the dismissal of the popup
     * @param isCancellable Boolean; can the user dismiss the popup
     * @return An AlertDialog instance with the provided text and properties.
     */
    public AlertDialog displayInfoDialogue(String title, String message, DialogInterface.OnDismissListener listener, boolean isCancellable) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(isCancellable);
        if(title != null) {
            alertBuilder.setTitle(title);
        }
        if(message != null) {
            alertBuilder.setMessage(message);
        }
        if(listener != null) {
            alertBuilder.setOnDismissListener(listener);
        }
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
        return dialog;
    }

    /**
     * Check if the device has network access.
     * @preconditions none
     * @modifies nothing
     * @return True if the device is connected, otherwise false.
     */
    public boolean hasNetworkAccess() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
