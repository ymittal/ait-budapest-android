package hu.ait.android.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private FrameLayout layoutContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutContainer = (FrameLayout) findViewById(R.id.layoutContainer);
        showFragment(FragmentMain.TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main:
                showFragment(FragmentMain.TAG);
                break;
            case R.id.action_details:
                showFragment(FragmentDetails.TAG);
                break;
            default:
                break;
        }
        return true;
    }

    public void showFragment(String tag) {
        Fragment fragment = null;
        fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if (tag.equals(FragmentDetails.TAG)) {
                fragment = new FragmentDetails();
            }
            else if (tag.equals(FragmentMain.TAG)) {
                fragment = new FragmentMain();
            }
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.layoutContainer, fragment, tag);
        // transaction.addToBackStack(null);
        transaction.commit();
    }
}
