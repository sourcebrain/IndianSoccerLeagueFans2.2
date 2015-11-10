package com.sourcebrain.islfans.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.sourcebrain.islfans.R;
import com.sourcebrain.islfans.model.Fixture;
import com.sourcebrain.islfans.model.Result;

import java.util.ArrayList;

public class ISLHomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = ISLHomeActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    public static ArrayList<Fixture> mArrayFixtures;
    public static ArrayList<Result> mArrayResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdBuddiz.setPublisherKey("5bc92d27-c67e-45d5-b2f6-355055ea2b56");
        AdBuddiz.cacheAds(this); // this = current Activity

        AdBuddiz.showAd(this); // this = current Activity

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mArrayFixtures = new ArrayList<Fixture>();
        mArrayResults = new ArrayList<Result>();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new FixturesFragment();
                title = getString(R.string.title_fixtures);
                break;
            case 1:
                fragment = new ResultsFragment();
                title = getString(R.string.title_results);
                break;
            case 2:
                fragment = new PointsFragment();
                title = getString(R.string.title_points);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}