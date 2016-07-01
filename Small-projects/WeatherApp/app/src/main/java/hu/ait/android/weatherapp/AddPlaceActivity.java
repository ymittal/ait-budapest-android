package hu.ait.android.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hu.ait.android.weatherapp.item.CitiesAdapter;
import hu.ait.android.weatherapp.model.City;

public class AddPlaceActivity extends AppCompatActivity {
    public static final String NEW_PLACE = "NEW_PLACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        setTitle(getString(R.string.activity_add_place_title));
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFinishOnTouchOutside(false);

        final EditText etPlace = (EditText) findViewById(R.id.etPlace);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPlace.getText().toString().equals("")) {
                    etPlace.setError(getString(R.string.et_place_error));
                } else {
                    Intent i = new Intent().putExtra(NEW_PLACE,
                            etPlace.getText().toString());
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED, new Intent());
        finish();
    }
}
