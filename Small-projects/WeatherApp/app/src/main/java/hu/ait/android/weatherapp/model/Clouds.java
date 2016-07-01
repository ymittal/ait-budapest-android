package hu.ait.android.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    @Expose
    public Double all;

    public Double getAll() {
        return all;
    }
}