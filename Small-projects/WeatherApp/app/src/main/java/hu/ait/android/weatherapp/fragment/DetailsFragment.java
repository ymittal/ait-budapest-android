package hu.ait.android.weatherapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.ait.android.weatherapp.R;
import hu.ait.android.weatherapp.Util;
import hu.ait.android.weatherapp.WeatherActivity;
import hu.ait.android.weatherapp.model.WeatherInfo;

public class DetailsFragment extends Fragment {
    private WeatherInfo weatherInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, null);
        weatherInfo = ((WeatherActivity) getActivity()).getWeatherInfo();

        initViews(rootView);

        return rootView;
    }

    private void initViews(View root) {
        TextView tvPlace = (TextView) root.findViewById(R.id.tvPlace);
        TextView tvDate = (TextView) root.findViewById(R.id.tvDate);
        TextView tvDesc = (TextView) root.findViewById(R.id.tvDesc);
        TextView tvPressure = (TextView) root.findViewById(R.id.tvPressure);
        TextView tvHumidity = (TextView) root.findViewById(R.id.tvHumidity);
        TextView tvWind = (TextView) root.findViewById(R.id.tvWind);
        TextView tvMaxTemp = (TextView) root.findViewById(R.id.tvMaxTemp);
        TextView tvMinTemp = (TextView) root.findViewById(R.id.tvMinTemp);


        tvPlace.setText(weatherInfo.getName());
        tvDate.setText(getResources().getString(R.string.tv_date,
                new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
        tvDesc.setText(Html.fromHtml(getResources().getString(R.string.tv_desc_main,
                weatherInfo.getWeather().get(0).getMain())));
        tvPressure.setText(Html.fromHtml(getResources().getString(R.string.tv_pressure,
                weatherInfo.getMain().getPressure().intValue())));
        tvHumidity.setText(Html.fromHtml(getResources().getString(R.string.tv_humid,
                weatherInfo.getMain().getHumidity().intValue())));
        tvWind.setText(Html.fromHtml(getResources().getString(R.string.tv_wind,
                weatherInfo.getWind().getSpeed(),
                Util.getWindUnit(getContext()))));
        tvMaxTemp.setText(Integer.toString(weatherInfo.getMain().getTempMax().intValue()) + '\u00B0');
        tvMinTemp.setText(Integer.toString(weatherInfo.getMain().getTempMin().intValue()) + '\u00B0');
    }
}
