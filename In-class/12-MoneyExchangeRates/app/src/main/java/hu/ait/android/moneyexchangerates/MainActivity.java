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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import hu.ait.android.moneyexchangerates.model.MoneyResult;
import hu.ait.android.moneyexchangerates.network.HttpGetTask;

public class MainActivity extends AppCompatActivity {
    private TextView tvData;

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

        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMoneyResult(MoneyResult moneyResult) {
        tvData.setText(moneyResult.getRates().gethUF().toString());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
