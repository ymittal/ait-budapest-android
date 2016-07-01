package hu.ait.android.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import hu.ait.android.weatherapp.item.CitiesAdapter;
import hu.ait.android.weatherapp.model.WeatherInfo;
import hu.ait.android.weatherapp.network.WeatherInfoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    private MaterialViewPager mViewPager;
    private String mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPlace = getIntent().getStringExtra(CitiesAdapter.PLACE_EXTRA);
        fetchWeatherInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setTitle("");
        setContentView(R.layout.activity_weather);
        initUI();
    }

    private void initUI() {
        mViewPager = (MaterialViewPager) findViewById(R.id.viewpager);

        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        initViewPager();
    }

    private void initViewPager() {
        mViewPager.getViewPager().setAdapter(
                new MyViewPagerAdaper(getSupportFragmentManager(), getApplicationContext()));

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.green,
                                getDrawable(R.drawable.ic_places));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.blue,
                                getDrawable(R.drawable.ic_places));
                    default:
                        return null;
                }
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(
                mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void fetchWeatherInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final WeatherInfoService service = retrofit.create(WeatherInfoService.class);
        service.getWeather(mPlace, getString(R.string.default_units), getString(R.string.owm_api_key))
                .enqueue(new Callback<WeatherInfo>() {
                    @Override
                    public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                        if (response.body().getCod() == 200
                                && mPlace.equalsIgnoreCase(response.body().getName())) {
                            Toast.makeText(WeatherActivity.this, response.body().getId().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WeatherActivity.this, "This place does not exist.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherInfo> call, Throwable t) {
                        Toast.makeText(WeatherActivity.this, getString(R.string.fetch_weather_fail, t.getMessage()),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

