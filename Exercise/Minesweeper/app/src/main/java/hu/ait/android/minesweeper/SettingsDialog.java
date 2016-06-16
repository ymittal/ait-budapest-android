package hu.ait.android.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsDialog extends AppCompatActivity {
    public static final String NEW_SIZE = "NEW_SIZE";
    public static final String NEW_NUM_MINES = "NEW_NUM_MINES";

    private TextView tvSize;
    private TextView tvFlags;
    private NumberPicker npSize;
    private NumberPicker npFlags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        setFinishOnTouchOutside(false);

        findViews();
        initSizeHalf();
        initFlagsHalf();

        npSize.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                initFlagsHalf();
            }
        });

        Button btnOkay = (Button) findViewById(R.id.btnOkay);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForChanges();
            }
        });
    }

    private void checkForChanges() {
        if (MainActivity.SIZE != npSize.getValue()
                || MainActivity.NUM_MINES != npFlags.getValue()) {
            Intent main = new Intent(this, MainActivity.class);
            main.putExtra(NEW_SIZE, npSize.getValue())
                    .putExtra(NEW_NUM_MINES, npFlags.getValue());
            startActivity(main);
        } else {
            finish();
        }
    }

    private void findViews() {
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvFlags = (TextView) findViewById(R.id.tvMines);
        npSize = (NumberPicker) findViewById(R.id.npSize);
        npFlags = (NumberPicker) findViewById(R.id.npMines);
    }

    private void initFlagsHalf() {
        npFlags.setMinValue((int) (0.5 * npSize.getValue()));
        npFlags.setMaxValue((int) (1.5 * npSize.getValue()));
        npFlags.setValue(MainActivity.NUM_MINES);
        tvFlags.setText(getString(R.string.tv_mines, npFlags.getValue()));
    }

    private void initSizeHalf() {
        npSize.setMinValue(4);
        npSize.setMaxValue(10);
        npSize.setValue(MainActivity.SIZE);
        tvSize.setText(getString(R.string.tv_size, npSize.getValue()));
    }

    @Override
    public void onBackPressed() {
        checkForChanges();
    }
}
