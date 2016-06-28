package hu.ait.android.moneyexchangerates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import hu.ait.android.moneyexchangerates.model.MoneyResult;
import hu.ait.android.moneyexchangerates.network.HttpGetTask;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_RATES_RESULT = "ACTION_RATES_RESULT";
    private TextView tvData;
    private RatesReceiver ratesReceiver = new RatesReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);

        Button btnGetRates = (Button) findViewById(R.id.btnGetRates);
        btnGetRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpGetTask(getApplicationContext()).execute(
                        "http://api.fixer.io/latest?base=INR");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                ratesReceiver, new IntentFilter(ACTION_RATES_RESULT)
        );
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                ratesReceiver);
        super.onStop();
    }

    private class RatesReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(HttpGetTask.KEY_RESULT);

            try {
                MoneyResult moneyResult = new Gson().fromJson(result, MoneyResult.class);
                tvData.setText("1 INR = " + moneyResult.getRates().gethUF() + " HUF on "
                        + moneyResult.getDate());

            } catch (Exception e) {
                tvData.setText(result + "\n" + e.getMessage());
            }
        }
    }
}
