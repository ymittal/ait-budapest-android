package hu.ait.android.weatherapp.network;

import hu.ait.android.weatherapp.model.WeatherInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInfoService {

    @GET("weather")
    Call<WeatherInfo> getWeather(@Query("q") String city,
                                 @Query("units") String units,
                                 @Query("appid") String appid);
}
