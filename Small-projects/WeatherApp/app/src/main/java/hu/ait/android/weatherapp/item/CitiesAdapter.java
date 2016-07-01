package hu.ait.android.weatherapp.item;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.weatherapp.R;
import hu.ait.android.weatherapp.WeatherActivity;
import hu.ait.android.weatherapp.gesture.CityTouchAdapter;
import hu.ait.android.weatherapp.model.City;

public class CitiesAdapter extends RecyclerView.Adapter<CityViewHolder> implements CityTouchAdapter {
    public static final String PLACE_EXTRA = "PLACE_EXTRA";
    private Context context;
    List<City> cities = new ArrayList<>();

    public CitiesAdapter(Context context) {
        this.context = context;
        cities = City.listAll(City.class);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new CityViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {
        City city = cities.get(position);
        holder.tvCity.setText(city.getName());
        holder.itemView.setTag(city);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(context, WeatherActivity.class);
                weatherIntent.putExtra(PLACE_EXTRA, cities.get(position).getName());
                context.startActivity(weatherIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        final City cityToBeRemoved = cities.get(position);
        cityToBeRemoved.delete();

        cities.remove(position);
        notifyItemRemoved(position);

        Snackbar.make(recyclerView, R.string.sb_alert, Snackbar.LENGTH_LONG)
                .setAction(R.string.sb_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cityToBeRemoved.save();
                        cities.add(position, cityToBeRemoved);
                        notifyItemInserted(position);
                    }
                }).show();
    }

    public void addItem(City newCity) {
        newCity.save();

        cities.add(newCity);
        notifyDataSetChanged();
    }
}
