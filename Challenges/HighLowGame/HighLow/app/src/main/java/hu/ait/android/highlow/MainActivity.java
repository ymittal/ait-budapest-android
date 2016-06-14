package hu.ait.android.highlow;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private Snackbar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout layoutRoot = (LinearLayout) findViewById(R.id.layoutRoot);
        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnHelp = (Button) findViewById(R.id.btnHelp);
        Button btnAbout = (Button) findViewById(R.id.btnAbout);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = Snackbar.make(layoutRoot, R.string.snackbar_help, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbar_btn_close, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sb.dismiss();
                            }
                        });
                sb.show();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(layoutRoot, R.string.snackbar_about, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void openGameActivity() {
        Intent game = new Intent();
        game.setClass(MainActivity.this, GameActivity.class);
        startActivity(game);
    }
}
