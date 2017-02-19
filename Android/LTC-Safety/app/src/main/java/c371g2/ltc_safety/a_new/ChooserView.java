package c371g2.ltc_safety.a_new;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import c371g2.ltc_safety.R;

/**
 *
 */
public class ChooserView{

    static void setup(Context context, final TextView view, final String title, final String[] choices) {
        assert(view != null);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==(MotionEvent.ACTION_DOWN)){
                    chooserAction(title, view, choices);
                    return true;
                }
                return false;
            }
        });
    }

    private static void chooserAction(String title, final TextView view, final String[] choices) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(title);

        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                dialog.dismiss();
                view.setText(choices[index]);
            }
        });
        builder.create().show();
    }

}
