package cmpt371group2.ltc_prototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class NewConcernActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_concern);

        Spinner concernTypeSpinner = (Spinner) findViewById(R.id.ConcernTypeSpinner);
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<String>(
            NewConcernActivity.this,
            android.R.layout.simple_list_item_1,
            getResources().getStringArray(R.array.concern_types)
        );
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        concernTypeSpinner.setAdapter(typeSpinnerAdapter);

        Button submitButton = (Button) findViewById(R.id.submit_concern_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewConcernActivity.this,
                "Concern Submission not implemented",
                Toast.LENGTH_LONG).show();
            }
        });
    }
}
