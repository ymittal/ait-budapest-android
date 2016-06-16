package hu.ait.android.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hu.ait.android.minesweeper.model.MinesweeperModel;
import hu.ait.android.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {
    public static int SIZE = 6;
    public static int NUM_MINES = 6;

    private TextView tvNumFlags;
    private ImageView emoticon;
    private MinesweeperView gameboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(SettingsDialog.NEW_SIZE)) {
            SIZE = getIntent().getIntExtra(SettingsDialog.NEW_SIZE, 6);
            NUM_MINES = getIntent().getIntExtra(SettingsDialog.NEW_NUM_MINES, 6);
            MinesweeperModel.getInstance().resetModel(SIZE, NUM_MINES);
        }
        setContentView(R.layout.activity_main);

        tvNumFlags = (TextView) findViewById(R.id.tvNumFlags);
        emoticon = (ImageView) findViewById(R.id.emoticon);

        gameboard = (MinesweeperView) findViewById(R.id.gameboard);
        gameboard.setEnabled(true);
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

    public void updateNumFlags(int numFlags) {
        tvNumFlags.setText(Integer.toString(numFlags));
    }

    public void gameOver(int resId) {
        emoticon.setImageResource(resId);
        gameboard.setEnabled(false);
    }
}
