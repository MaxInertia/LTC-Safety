package c371g2.ltc_safety;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Class creates a popup dialog box which contain custom information for the user.
 * @Invariants no instances of this class exist
 * @HistoryProperties none
 */
public class InfoDialog {

    /**
     * Constructor to prevent initializing instances of the class.
     */
    private InfoDialog(){}

    /**
     * Display a popup dialog that can contain a title and/or a message. A listener can be linked to
     * the popup; the operation in the listener will be performed when the popup is dismissed.
     * @preconditions none
     * @modifies nothing
     * @param title Title of the popup
     * @param message Text in the popup, appears under the title
     * @param listener The listener for the dismissal of the popup
     * @param isCancellable Boolean; can the user dismiss the popup
     * @return An AlertDialog instance with the provided text and properties.
     */
    public static AlertDialog createInfoDialogue(Context context,
                                                  String title,
                                                  String message,
                                                  DialogInterface.OnDismissListener listener,
                                                  boolean isCancellable) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
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
        return dialog;
    }

}
