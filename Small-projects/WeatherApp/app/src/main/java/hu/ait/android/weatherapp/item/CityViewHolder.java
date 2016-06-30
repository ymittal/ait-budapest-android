package hu.ait.android.weatherapp.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import hu.ait.android.weatherapp.R;

public class CityViewHolder extends RecyclerView.ViewHolder {
    public final TextView tvCity;

    public CityViewHolder(View itemView) {
        super(itemView);
        tvCity = (TextView) itemView.findViewById(R.id.tvCity);
    }
}
