package c371g2.ltc_safety.a_new;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import c371g2.ltc_safety.R;

/**
 * The class responsible for setting up the popup selection window for Concern Nature and Facility Name.
 * @Invariants no instances of this class exist.
 * @HistoryProperties none
 */
class ChooserView{

    /**
     * Constructor to prevent initializing instances of the class.
     */
    private ChooserView(){}

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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooserAction(view, title, choices);
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
        System.out.println("onClickCalled!");
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        if(title == null) {
            builder.setTitle(title);
        }

        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                dialog.dismiss();
                assert (view != null);
                if(choices[index].equals("Other")) {
                    customInputPopup(view);
                } else {
                    view.setText(choices[index]);
                }
            }
        });
        builder.create().show();
    }

    /**
     * Display a popup for the user to enter a custom string value.
     * @param view The view whose text will be set to the custom string value.
     */
    private static void customInputPopup(final TextView view) {
        Context context = view.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input Concern Nature");

        final EditText input = new EditText(view.getContext());
        input.setTextSize(20);
        input.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        input.setGravity(Gravity.CENTER);
        assert(input != null);

        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                view.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                assert(dialog != null);
                dialog.cancel();
            }
        });

        assert(builder != null);
        builder.create().show();
    }

}
