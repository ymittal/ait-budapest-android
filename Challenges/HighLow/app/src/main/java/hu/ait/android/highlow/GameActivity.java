package hu.ait.android.highlow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {
    public static final String KEY_NUM_TRY = "KEY_NUM_TRY";
    private int numTry = 0;
    private int target;

    @BindView(R.id.etGuess) EditText etGuess;
    @BindView(R.id.tvNumTry) TextView tvNumTry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        target = new Random().nextInt(100);
    }

    @OnClick(R.id.btnGuess)
    public void guess(View view) {
        numTry += 1;
        tvNumTry.setText(getString(R.string.tv_num_try, numTry));

        int guess = Integer.parseInt(etGuess.getText().toString());
        if (target == guess) {
            openWinnerDialog();
        } else if (target > guess) {
            Toast.makeText(GameActivity.this, R.string.toast_guess_small, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(GameActivity.this, R.string.toast_guess_large, Toast.LENGTH_SHORT).show();
        }
    }

    private void openWinnerDialog() {
        Intent dialog = new Intent();
        dialog.setClass(GameActivity.this, winnerDialog.class);
        dialog.putExtra(KEY_NUM_TRY, Integer.toString(numTry));
        startActivity(dialog);
    }
}
