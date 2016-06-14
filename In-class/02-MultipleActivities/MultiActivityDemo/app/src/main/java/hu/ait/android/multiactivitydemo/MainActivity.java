package hu.ait.android.multiactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_ADDRESS = "KEY_ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etAddress = (EditText) findViewById(R.id.etAddress);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetails = new Intent();
                intentDetails.setClass(MainActivity.this,
                        DetailActivity.class);
                intentDetails.putExtra(KEY_USERNAME, etUsername.getText().toString())
                        .putExtra(KEY_ADDRESS, etAddress.getText().toString());
                startActivity(intentDetails);
            }
        });
    }
}
