package c371g2.ltc_safety.a_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import c371g2.ltc_safety.R;
import c371g2.ltc_safety.local.ConcernWrapper;
import c371g2.ltc_safety.local.StatusWrapper;

/**
 *
 */
public class ConcernStatusListAdapter extends ArrayAdapter<StatusWrapper>{
    /**
     * Constructor for the ConcernListAdapter class.
     * @param context The context of MainActivity.class
     * @param resource The layout for each row of the list
     * @param concerns The list of concerns that will populate the list
     */
    public ConcernStatusListAdapter(Context context, int resource, List<StatusWrapper> concerns) {
        super(context, resource, concerns);
    }

    @Nonnull
    @Override
    public View getView(int position, View convertView, @Nonnull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        StatusWrapper status = getItem(position);
        View concernListItem;

        if(status != null) {
            concernListItem = inflater.inflate(R.layout.detailed_concern_status, parent, false);

            ((TextView) concernListItem.findViewById(R.id.detailedConcern_statusType)).setText(
                    status.getType()
            );
            ((TextView) concernListItem.findViewById(R.id.detailedConcern_statusDate)).setText(
                    status.getFormattedDate()
            );
        } else {
            concernListItem = inflater.inflate(R.layout.detailed_concern_status, parent, false);
        }

        return concernListItem;
    }
}
