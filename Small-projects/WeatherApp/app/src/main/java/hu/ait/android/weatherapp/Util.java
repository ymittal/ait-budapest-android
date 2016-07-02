package hu.ait.android.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

public class Util {
    public static String getPreferredUnits(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_units_key),
                context.getString(R.string.default_units));
    }

    public static String getWindUnit(Context context) {
        String preferredUnit = getPreferredUnits(context);
        if (preferredUnit.equals(context.getString(R.string.default_units)))
            return context.getString(R.string.wind_metric_unit);
        else
            return context.getString(R.string.wind_imperial_unit);
    }
}
