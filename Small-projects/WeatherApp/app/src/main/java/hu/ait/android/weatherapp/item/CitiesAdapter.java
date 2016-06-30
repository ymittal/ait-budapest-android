package hu.ait.android.weatherapp.item;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.weatherapp.R;
import hu.ait.android.weatherapp.gesture.CityTouchAdapter;
import hu.ait.android.weatherapp.model.City;

public class CitiesAdapter extends RecyclerView.Adapter<CityViewHolder> implements CityTouchAdapter {
    List<City> Cities = new ArrayList<>();

    public CitiesAdapter() {
        for(int i = 0; i <20; ++i)
            Cities.add(new City("City " + i));
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new CityViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, int position) {
        City city = Cities.get(position);
        holder.tvCity.setText(city.getName());
        holder.itemView.setTag(city);
    }

    @Override
    public int getItemCount() {
        return Cities.size();
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        final City cityToBeRemoved = Cities.get(position);
        Cities.remove(position);
        notifyItemRemoved(position);

        // TODO: extract (parameter) string resources
        Snackbar.make(recyclerView, "City deleted.", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cities.add(position, cityToBeRemoved);
                        notifyItemInserted(position);
                    }
                }).show();
    }

    public void addItem(City newCity) {
        Cities.add(newCity);
        notifyDataSetChanged();
    }
}