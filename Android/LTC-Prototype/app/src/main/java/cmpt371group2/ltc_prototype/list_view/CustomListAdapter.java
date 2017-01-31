package cmpt371group2.ltc_prototype.list_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cmpt371group2.ltc_prototype.R;

public class CustomListAdapter extends ArrayAdapter<ListRow> {

    public CustomListAdapter(Context context, ListRow[] options) {
        super(context, R.layout.list_item , options);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater itemInflater = LayoutInflater.from(getContext());
        ListRow item = getItem(position);
        View customView;

        if(item!=null) {
            customView = itemInflater.inflate(item.getLayout(), parent, false);
            TextView itemText = (TextView) customView.findViewById(R.id.list_text);
            ImageView itemImage = (ImageView) customView.findViewById(R.id.list_image);
            itemText.setText(item.getText());
            itemImage.setImageResource(item.getImage());
        } else {
            customView = itemInflater.inflate(R.layout.list_item, parent, false);
        }
        return customView;
    }
}
