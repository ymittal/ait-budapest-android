package hu.ait.android.moneyexchangerates.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoneyResult {
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("rates")
    @Expose
    public Rates rates;

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public Rates getRates() {
        return rates;
    }
}