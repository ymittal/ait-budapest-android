package hu.ait.android.minesweeper;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hu.ait.android.minesweeper.model.MinesweeperModel;
import hu.ait.android.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {
    private TextView tvFlagsLeft;
    private ImageView emoticon;
    private MinesweeperView gameboard;
    private Chronometer timer;

    static int SIZE = MinesweeperModel.SIZE;
    static int NUM_MINES = MinesweeperModel.NUM_MINES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(SettingsDialog.NEW_SIZE)) {
            SIZE = getIntent().getIntExtra(SettingsDialog.NEW_SIZE, 6);
            NUM_MINES = getIntent().getIntExtra(SettingsDialog.NEW_NUM_MINES, 6);
            MinesweeperModel.getInstance().resetModel(SIZE, NUM_MINES);
        }
        setContentView(R.layout.activity_main);
        findViews();

        timer.start();

        emoticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinesweeperModel.getInstance().resetModel(SIZE, NUM_MINES);
                gameboard.invalidate();
                gameOver(R.drawable.face);
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
            }
        });
    }

    private void findViews() {
        tvFlagsLeft = (TextView) findViewById(R.id.tvFlagsLeft);
        gameboard = (MinesweeperView) findViewById(R.id.gameboard);
        emoticon = (ImageView) findViewById(R.id.emoticon);
        timer = (Chronometer) findViewById(R.id.timer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSettings:
                startActivity(new Intent(this, SettingsDialog.class));
                break;
            case R.id.actionHelp:
                Toast.makeText(MainActivity.this, R.string.toast_help, Toast.LENGTH_LONG).show();
                break;
            case R.id.actionAbout:
                Toast.makeText(MainActivity.this, R.string.toast_about, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateFlagsLeft(int flagsLeft) {
        tvFlagsLeft.setText(Integer.toString(flagsLeft));
    }

    public void gameOver(int resId) {
        emoticon.setImageResource(resId);
        timer.stop();
    }
}
