package cmpt371group2.ltc_prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public enum Action{
        Create_New_Concern("Create new Concern"),
        View_Old_Concerns("View old Concerns");
        String id;
        Action(String id){
            this.id = id;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] options = {"Create new Concern", "View old Concerns"};
        ListAdapter optionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, options);
        ListView optionListView = (ListView) findViewById(R.id.options_list_view);
        optionListView.setAdapter(optionsAdapter);

        optionListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String option = String.valueOf(parent.getItemAtPosition(position));

                        if(option.equals(Action.Create_New_Concern.id)) {
                            Intent newConcernIntent = new Intent(MainActivity.this, NewConcernActivity.class);
                            MainActivity.this.startActivity(newConcernIntent);

                        } else if(option.equals(Action.View_Old_Concerns.id)) {
                            Toast.makeText(MainActivity.this, "Not Implemented", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );



    }

}
