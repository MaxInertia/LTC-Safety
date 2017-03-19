package c371g2.ltc_safety;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

/**
 * The abstract class extended by each activity whose ViewModel performs a network operation.
 * MainActivity's ViewModel: Concern fetching (updating statuses).
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
    public Dialog progressDialog;

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
