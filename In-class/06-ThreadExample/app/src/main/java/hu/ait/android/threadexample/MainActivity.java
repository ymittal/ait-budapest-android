package hu.ait.android.threadexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tvData;
    private int count = 0;
    private boolean enabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);

        Button btnThread = (Button) findViewById(R.id.btnThread);
        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enabled = true;
                new MyCountThread().start();
                Toast.makeText(MainActivity.this, "Hello, world!", Toast.LENGTH_SHORT).show();

                resetTimer = new Timer();
                resetTimer.schedule(new MyResetTask(), 10000, 10000);
            }
        });
    }

    @Override
    protected void onStop() {
        enabled = false;
        if (resetTimer != null) {
            resetTimer.cancel();
        }

        super.onStop();
    }

    private class MyResetTask extends TimerTask {
        @Override
        public void run() {
            count = 0;
        }
    }

    private Timer resetTimer = null;

    private class MyCountThread extends Thread {
        public void run() {
            while (enabled) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvData.setText("Counter: " + (++count));
                        Toast.makeText(MainActivity.this, "Counter: " + count, Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
