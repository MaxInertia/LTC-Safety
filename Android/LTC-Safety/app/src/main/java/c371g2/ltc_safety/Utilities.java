package c371g2.ltc_safety;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import c371g2.ltc_safety.a_new.NewConcernActivity;

/**
 *
 */
public class Utilities {

    public static boolean hasNetworkAccess(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Display a popup. This can contain a title and/or a message. A listener can be linked to the
     * popup; the operation in the listener will be performed when the popup is dismissed.
     * @param title Title of the popup
     * @param message Text in the popup
     * @param listener The listener for the dismissal of the popup
     * @param isCancellable Boolean; can the user dismiss the popup
     */
    public static AlertDialog displayInfoDialogue(Activity activity, String title, String message, DialogInterface.OnDismissListener listener, boolean isCancellable) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
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
}
