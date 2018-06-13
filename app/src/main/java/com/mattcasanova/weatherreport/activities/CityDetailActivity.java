package com.mattcasanova.weatherreport.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.mattcasanova.weatherreport.R;

/**
 * An activity representing a single City detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CityListActivity}.
 */
public class CityDetailActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        // Get references to my views
        Toolbar toolbar          = findViewById(R.id.detail_toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        //Set up my action bar
        setSupportActionBar(toolbar);

        //Set the Listener on my fab
        fab.setOnClickListener(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // if we don't have a saved state, Create the detail fragment and add it to the activity
        // using a fragment transaction.
        if (savedInstanceState == null) {


            String CITY_PARAM_KEY = getString(R.string.city_param_key);
            String itemId         =  getIntent().getStringExtra(CITY_PARAM_KEY);

            Bundle arguments      = new Bundle();
            arguments.putString(CITY_PARAM_KEY, itemId);

            CityDetailFragment fragment = new CityDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.city_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, CityListActivity.class);
            navigateUpTo(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
