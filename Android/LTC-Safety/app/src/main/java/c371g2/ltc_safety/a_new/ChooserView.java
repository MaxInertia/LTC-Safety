package c371g2.ltc_safety.a_new;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * The class responsible for setting up the popup selection window for Concern Nature and Facility Name.
 * @Invariants no instances of this class exist.
 * @HistoryProperties none
 */
class ChooserView{
    private ChooserView(){};

    /**
     * Turns 'view' into a popup-selection trigger. When the view is clicked a popup selection window
     * will appear with the provided title and list of items 'choices'. A choice can be selected.
     * @preconditions
     * - view and choices are non-null.
     * @param view The TextView being 'converted' into a popup-selection trigger.
     * @param title The title of the popup.
     * @param choices The choices to be displayed in the popup.
     */
    static void setup(@NonNull final TextView view, final String title, @NonNull final String[] choices) {
        assert(view != null);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==(MotionEvent.ACTION_DOWN)){
                    chooserAction(view, title, choices);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Defines the onClickListener for each item in the list of choices.
     * @param view The TextView being 'converted' into a popup-selection trigger.
     * @param title The title of the popup.
     * @param choices The choices to be displayed in the popup.
     */
    private static void chooserAction(final TextView view, String title, final String[] choices) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        if(title == null) {
            builder.setTitle(title);
        }

        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                dialog.dismiss();
                assert(view != null);
                view.setText(choices[index]);
            }
        });
        builder.create().show();
    }

}
