package hu.ait.android.multiactivitydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import hu.ait.android.multiactivitydemo.data.User;
import hu.ait.android.multiactivitydemo.data.UserDataManager;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvDetails = (TextView) findViewById(R.id.tvDetails);

        /*if(getIntent().hasExtra(MainActivity.KEY_USER)) {
            User user = (User) getIntent().getSerializableExtra(MainActivity.KEY_USER);

            tvDetails.setText(getString(R.string.tv_details, user.getUsername(), user.getAddress()));
        }*/

        tvDetails.setText(getString(R.string.tv_details,
                UserDataManager.getInstance().getUser().getUsername(),
                UserDataManager.getInstance().getUser().getAddress()));
    }
}
