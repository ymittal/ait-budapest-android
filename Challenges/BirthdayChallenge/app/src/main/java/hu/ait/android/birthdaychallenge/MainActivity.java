package hu.ait.android.birthdaychallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDays = (Button) findViewById(R.id.btnDays);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setMaxDate(System.currentTimeMillis());

        btnDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().equals("")) {
                    etName.setError(getString(R.string.error_name));
                } else {
                    String message = "";

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR); // current year
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();
                    calendar.set(year, month, day);

                    // add one year if birthday already passed this year
                    if (calculateDaysToNextBirthday(calendar.getTime()) < 0) {
                        calendar.set(year + 1, month, day);
                    }
                    Long numDays = calculateDaysToNextBirthday(calendar.getTime());
                    if (numDays == 0) {
                        message = etName.getText().toString() +
                                getString(R.string.toast_bday);
                    }
                    else {
                        message = etName.getText().toString() +
                                getString(R.string.toast_num_days_pre) +
                                Long.toString(numDays) +
                                getString(R.string.toast_num_days_post);
                    }
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long calculateDaysToNextBirthday(Date birthday) {
        return TimeUnit.MILLISECONDS.toDays(birthday.getTime() - System.currentTimeMillis());
    }
}
