package hu.ait.android.weatherapp.fragment;

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

import hu.ait.android.weatherapp.R;
import hu.ait.android.weatherapp.gesture.CityTouchHelperCallback;
import hu.ait.android.weatherapp.item.CitiesAdapter;

public class PlacesFragment extends Fragment {
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
                // TODO: launch a city-adder activity to save city using SugarORM
                Toast.makeText(getContext(), "FAB clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
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
