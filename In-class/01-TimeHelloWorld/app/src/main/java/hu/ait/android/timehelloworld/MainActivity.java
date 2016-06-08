package hu.ait.android.timehelloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTime = (Button) findViewById(R.id.btnTime);
        final TextView tvTime = (TextView) findViewById(R.id.tvTime);
        final EditText etName = (EditText) findViewById(R.id.etName);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().equals("")) {
                    etName.setError(getString(R.string.error_name));
                } else {
                    String currentTime = etName.getText() +
                            getString(R.string.time_header) +
                            new Date(System.currentTimeMillis()).toString();

                    Toast.makeText(MainActivity.this,
                            currentTime,
                            Toast.LENGTH_LONG).show();

                    tvTime.setText(currentTime);
                }
            }
        });
    }
}
