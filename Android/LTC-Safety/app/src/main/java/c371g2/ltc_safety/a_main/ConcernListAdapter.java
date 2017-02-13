package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.local.Concern;

/**
 * This class is used to populate the concern list on the main activity with the previously
 * submitted concerns. It is only instantiated and used in the MainActivity class.
 * @Invariants none
 * @HistoryProperties none
 */
public class ConcernListAdapter extends ArrayAdapter<Concern> {

    /**
     * Constructor for the ConcernListAdapter class.
     * @param context The context of MainActivity.class
     * @param resource The layout for each row of the list
     * @param concerns The list of concerns that will populate the list
     */
    public ConcernListAdapter(Context context, int resource, ArrayList<Concern> concerns) {
        super(context, resource, concerns);
    }

    @Nonnull
    @Override
    public View getView(int position, View convertView,@Nonnull ViewGroup parent){
        return null;
    }

}
