package c371g2.ltc_safety.a_main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.local.ConcernWrapper;

/**
 * This class is used to populate the concern list on the main activity with the previously
 * submitted concerns. It is only instantiated and used in the MainActivity class.
 * @Invariants none
 * @HistoryProperties none
 */
public class ConcernListAdapter extends ArrayAdapter<ConcernWrapper> {

    /**
     * Constructor for the ConcernListAdapter class.
     * @preconditions
     * - context is not null
     * - resource is the value of a layout resource: R.layout____
     * - concerns is not null
     * @modifies nothing.
     * @param context The context of MainActivity.class
     * @param resource The layout for each row of the list
     * @param concerns The list of concerns that will populate the list
     */
    public ConcernListAdapter(@NonNull Context context, int resource,@NonNull List<ConcernWrapper> concerns) {
        super(context, resource, concerns);
    }

    @Nonnull
    @Override
    public View getView(int position, View convertView,@Nonnull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        assert(inflater != null);
        ConcernWrapper concern = getItem(position);
        View concernListItem;

        if(concern != null) {
            concernListItem = inflater.inflate(R.layout.concern_list_item, parent, false);
            assert(concern.getConcernType() != null);
            assert(concern.getFacilityName() != null);
            ((TextView) concernListItem.findViewById(R.id.concernListItem_concernType)).setText(
                    concern.getConcernType()
            );
            ((TextView) concernListItem.findViewById(R.id.concernListItem_facility)).setText(
                    concern.getFacilityName()
            );

            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy, hh:mm a");
            Date date2 = new Date(concern.getSubmissionDate());
            String dateString = sdf.format(date2);
            assert(dateString != null);
            ((TextView) concernListItem.findViewById(R.id.concernListItem_dateSubmitted)).setText(
                    dateString
            );
        } else {
            concernListItem = inflater.inflate(R.layout.concern_list_item, parent, false);
        }
        assert(concernListItem != null);
        return concernListItem;
    }

}
