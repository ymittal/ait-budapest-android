package hu.ait.android.tictactoe;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import hu.ait.android.tictactoe.view.TicTacToeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout layoutRoot = (LinearLayout) findViewById(R.id.layoutRoot);
        final TicTacToeView gameView = (TicTacToeView) findViewById(R.id.gameView);

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
                                    }
                                }).show();
            }
        });
    }

    public void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
