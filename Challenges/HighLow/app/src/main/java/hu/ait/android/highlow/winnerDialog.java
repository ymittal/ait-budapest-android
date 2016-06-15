package hu.ait.android.highlow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class winnerDialog extends AppCompatActivity {
    @BindView(R.id.tvCongrats) TextView tvCongrats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_dialog);
        ButterKnife.bind(this);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT); // resize dialog appropriately
        this.setFinishOnTouchOutside(false); // disable touch outside dialog

        tvCongrats.setText(getString(R.string.tv_congrats, getIntent().getStringExtra(GameActivity.KEY_NUM_TRY)));
    }

    @OnClick(R.id.btnOkay)
    public void okay(View view) {
        openMainActivity();
    }

    private void openMainActivity() {
        Intent main = new Intent(winnerDialog.this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }

    @Override
    public void onBackPressed() {
        openMainActivity();
    }
}
