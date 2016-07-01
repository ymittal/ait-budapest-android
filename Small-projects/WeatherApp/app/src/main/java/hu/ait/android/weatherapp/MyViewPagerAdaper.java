package hu.ait.android.weatherapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.ait.android.weatherapp.fragment.BasicInfoFragment;
import hu.ait.android.weatherapp.fragment.DetailsFragment;

public class MyViewPagerAdaper extends FragmentPagerAdapter {
    private Context context;

    public MyViewPagerAdaper(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    public MyViewPagerAdaper(FragmentManager supportFragmentManager, Context context) {
        super(supportFragmentManager);
        this.context = context;
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
        switch (position) {
            case 0:
                return context.getString(R.string.viewpager_basic_tab);
            case 1:
                return context.getString(R.string.viewpager_detailed_tab);
            default:
                return context.getString(R.string.viewpager_default_tab);
        }
    }
}
