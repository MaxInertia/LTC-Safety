package cmpt371group2.ltc_prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import cmpt371group2.ltc_prototype.list_view.CustomListAdapter;
import cmpt371group2.ltc_prototype.list_view.ListRow;

public class MainActivity extends AppCompatActivity {

    public enum Action{
        Create_Concern("New Concern"),
        View_Concerns("View Concerns");
        String id;
        Action(String id){
            this.id = id;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListRow[] items = {
                new ListRow(Action.Create_Concern.id, R.mipmap.new_concern_img, R.layout.list_item),
                new ListRow(Action.View_Concerns.id, R.mipmap.view_concerns_img, R.layout.list_item),
                null, null, null, null
        };

        CustomListAdapter optionsAdapter = new CustomListAdapter(this, items);
        ListView optionListView = (ListView) findViewById(R.id.options_list_view);
        optionListView.setAdapter(optionsAdapter);

        optionListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListRow item = (ListRow) parent.getItemAtPosition(position);

                    if(item.getText().equals(Action.Create_Concern.id)) {
                        Intent newConcernIntent = new Intent(MainActivity.this, NewConcernActivity.class);
                        MainActivity.this.startActivity(newConcernIntent);

                    } else if(item.getText().equals(Action.View_Concerns.id)) {
                        Toast.makeText(MainActivity.this, "Not Implemented", Toast.LENGTH_LONG).show();
                    }
                }
            }
        );

    }

}
