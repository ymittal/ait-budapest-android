package hu.ait.android.multiactivitydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvDetails = (TextView) findViewById(R.id.tvDetails);

        if(getIntent().hasExtra(MainActivity.KEY_USERNAME)
                && getIntent().hasExtra(MainActivity.KEY_ADDRESS)) {
            String username = getIntent().getStringExtra(MainActivity.KEY_USERNAME);
            String address = getIntent().getStringExtra(MainActivity.KEY_ADDRESS);

            tvDetails.setText(getString(R.string.tv_details, username, address));
        }
    }
}
