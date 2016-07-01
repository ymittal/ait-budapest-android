package hu.ait.android.weatherapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import hu.ait.android.weatherapp.AddPlaceActivity;
import hu.ait.android.weatherapp.R;
import hu.ait.android.weatherapp.gesture.CityTouchHelperCallback;
import hu.ait.android.weatherapp.item.CitiesAdapter;
import hu.ait.android.weatherapp.model.City;

public class PlacesFragment extends Fragment {
    private int REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private CitiesAdapter citiesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_places, null);

        initRecyclerView(rootView);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), AddPlaceActivity.class),
                        REQUEST_CODE);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                City newCity = new City(data.getStringExtra(AddPlaceActivity.NEW_PLACE));
                citiesAdapter.addItem(newCity);
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), R.string.toast_add_place_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initRecyclerView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        citiesAdapter = new CitiesAdapter();
        recyclerView.setAdapter(citiesAdapter);

        ItemTouchHelper.Callback callback =
                new CityTouchHelperCallback(citiesAdapter, recyclerView);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }
}
