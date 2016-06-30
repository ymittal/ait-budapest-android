package hu.ait.android.weatherapp.model;

import com.orm.SugarRecord;

public class City extends SugarRecord {
    private String name;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
