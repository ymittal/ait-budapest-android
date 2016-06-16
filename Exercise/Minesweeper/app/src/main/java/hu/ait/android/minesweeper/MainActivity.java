package hu.ait.android.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import hu.ait.android.minesweeper.model.MinesweeperModel;

public class MainActivity extends AppCompatActivity {
    public static int SIZE = 6;
    public static int NUM_MINES = 6;

    private TextView tvNumFlags;

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SettingsDialog.NEW_SIZE, SIZE);
        outState.putInt(SettingsDialog.NEW_NUM_MINES, NUM_MINES);
        super.onSaveInstanceState(outState);
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
                Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_LONG).show();
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
}
