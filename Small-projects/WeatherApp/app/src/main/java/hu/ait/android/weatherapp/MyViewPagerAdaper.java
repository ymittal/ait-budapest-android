package hu.ait.android.weatherapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.ait.android.weatherapp.fragment.BasicInfoFragment;
import hu.ait.android.weatherapp.fragment.DetailsFragment;

public class MyViewPagerAdaper extends FragmentPagerAdapter {
    public MyViewPagerAdaper(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BasicInfoFragment();
            case 1:
                return new DetailsFragment();
            default:
                return new BasicInfoFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Todo: extract strings
        switch (position) {
            case 0:
                return "Basic Information";
            case 1:
                return "Detailed";
            default:
                return "Default";
        }
    }
}
