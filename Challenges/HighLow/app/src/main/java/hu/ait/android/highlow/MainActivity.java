package hu.ait.android.highlow;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private Snackbar sb;
    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        root = (LinearLayout) findViewById(R.id.layoutRoot);
    }

    @OnClick(R.id.btnStart)
    public void start(View view) {
        openGameActivity();
    }

    @OnClick(R.id.btnHelp)
    public void about(View view) {
        sb = Snackbar.make(root, R.string.snackbar_help, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_btn_close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sb.dismiss();
                    }
                });
        sb.show();
    }

    @OnClick(R.id.btnAbout)
    public void help(View view) {
        Snackbar.make(root, R.string.snackbar_about, Snackbar.LENGTH_SHORT).show();
    }

    private void openGameActivity() {
        Intent game = new Intent();
        game.setClass(MainActivity.this, GameActivity.class);
        startActivity(game);
    }
}
