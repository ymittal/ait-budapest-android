package hu.ait.android.weatherapp.gesture;

import android.support.v7.widget.RecyclerView;

public interface CityTouchAdapter {
    void onItemDismiss(int position, RecyclerView recyclerView);
}