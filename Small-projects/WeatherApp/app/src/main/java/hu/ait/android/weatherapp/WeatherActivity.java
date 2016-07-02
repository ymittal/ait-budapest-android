package hu.ait.android.weatherapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    private WeatherInfo weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPlace = getIntent().getStringExtra(CitiesAdapter.PLACE_EXTRA);
        fetchWeatherInfo();
    }

    private void initUI() {
        setTitle("");
        setContentView(R.layout.activity_weather);

        mViewPager = (MaterialViewPager) findViewById(R.id.viewpager);
        initViewPager();
        setPagerLogo();

        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void setPagerLogo() {
        ImageView ivLogo = (ImageView) findViewById(R.id.ivLogo);
        Glide.with(this).load(getString(R.string.url_weather_image,
                weatherInfo.getWeather().get(0).getIcon()))
                .into(ivLogo);
    }

    private void initViewPager() {
        mViewPager.getViewPager().setAdapter(
                new MyViewPagerAdaper(getSupportFragmentManager(), getApplicationContext()));

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.viewpagerOne,
                                getDrawable(R.drawable.bg_1));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.viewpagerTwo,
                                getDrawable(R.drawable.bg_2));
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
        service.getWeather(mPlace, Util.getPreferredUnits(getApplicationContext()), getString(R.string.owm_api_key))
                .enqueue(new Callback<WeatherInfo>() {
                    @Override
                    public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                        if (response.body().getCod() == 200
                                && mPlace.equalsIgnoreCase(response.body().getName())) {
                            weatherInfo = response.body();
                            initUI();
                        } else {
                            toastToFinish(getString(R.string.error_no_place));
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherInfo> call, Throwable t) {
                        toastToFinish(getString(R.string.fetch_weather_fail));
                    }
                });
    }

    private void toastToFinish(String message) {
        Toast.makeText(WeatherActivity.this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }
}

