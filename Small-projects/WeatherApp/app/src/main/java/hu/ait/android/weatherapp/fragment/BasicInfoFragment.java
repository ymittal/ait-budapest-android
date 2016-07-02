package hu.ait.android.weatherapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.ait.android.weatherapp.R;
import hu.ait.android.weatherapp.WeatherActivity;
import hu.ait.android.weatherapp.model.WeatherInfo;

public class BasicInfoFragment extends Fragment implements OnMapReadyCallback {
    private static final float ZOOM_LEVEL = 10f;

    private WeatherInfo weatherInfo;
    private GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basic_info, null);
        weatherInfo = ((WeatherActivity) getActivity()).getWeatherInfo();
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        initViews(rootView);

        return rootView;
    }

    private void initViews(View root) {
        TextView tvPlace = (TextView) root.findViewById(R.id.tvPlace);
        TextView tvDate = (TextView) root.findViewById(R.id.tvDate);
        TextView tvDesc = (TextView) root.findViewById(R.id.tvDesc);
        TextView tvTemp = (TextView) root.findViewById(R.id.tvTemp);

        tvPlace.setText(weatherInfo.getName());
        tvDate.setText(getResources().getString(R.string.tv_date,
                new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
        tvDesc.setText(Html.fromHtml(getResources().getString(R.string.tv_desc_main,
                weatherInfo.getWeather().get(0).getMain())));
        tvTemp.setText(Html.fromHtml(getResources().getString(R.string.tv_temp,
                weatherInfo.getMain().getTemp())));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(weatherInfo.getCoord().getLat(),
                weatherInfo.getCoord().getLon());

        map = googleMap;
        map.addMarker(new MarkerOptions().position(location)
                .title(weatherInfo.getName())
                .snippet(weatherInfo.getWeather().get(0).getDescription()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM_LEVEL));
    }
}
