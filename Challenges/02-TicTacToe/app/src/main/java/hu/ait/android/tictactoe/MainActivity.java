package hu.ait.android.tictactoe;

import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

import hu.ait.android.tictactoe.model.TicTacToeModel;
import hu.ait.android.tictactoe.view.TicTacToeView;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long timeCircle = 0;
    private long timeCross = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout layoutRoot = (LinearLayout) findViewById(R.id.layoutRoot);
        final TicTacToeView gameView = (TicTacToeView) findViewById(R.id.gameView);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // asks (snackbar message) if user is sure
                Snackbar.make(layoutRoot, getString(R.string.snackbar_ques), Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_snackbar,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        gameView.clearGameArea();
                                        resetTimes();
                                    }
                                }).show();
            }
        });
    }

    private void resetTimes() {
        timeCircle = 0;
        timeCross = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public void switchChronometerTime() {
        if (TicTacToeModel.getInstance().getNextPlayer() == TicTacToeModel.CIRCLE) {
            timeCircle += SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.setBase(SystemClock.elapsedRealtime());
        } else {
            timeCross += SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
    }

    public void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void stopChronometer() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        showToastMessage(getString(R.string.toast_timings,
                (int) timeCircle / 1000, (int) timeCross / 1000));
    }
}
