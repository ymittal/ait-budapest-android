package hu.ait.android.moneyexchangerates;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hu.ait.android.moneyexchangerates.model.MoneyResult;
import hu.ait.android.moneyexchangerates.network.MoneyRatesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.fixer.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final MoneyRatesService moneyRatesService = retrofit.create(
                MoneyRatesService.class);

        Button btnGetRates = (Button) findViewById(R.id.btnGetRates);
        btnGetRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moneyRatesService.getRates("USD").enqueue(new Callback<MoneyResult>() {
                    @Override
                    public void onResponse(Call<MoneyResult> call, Response<MoneyResult> response) {
                        tvData.setText("1 USD = " + response.body().getRates().gethUF()
                                + " HUF on " + response.body().getDate());
                    }

                    @Override
                    public void onFailure(Call<MoneyResult> call, Throwable t) {
                        tvData.setText(t.getMessage());
                    }
                });
            }
        });
    }
}
