package com.mattcasanova.weatherreport.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.adaptors.CityAdapter;
import com.mattcasanova.weatherreport.dummy.DummyContent;

/**
 * An activity representing a list of Cities. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CityDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CityListActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean mIsTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        //Get References to my views
        Toolbar              toolbar         = findViewById(R.id.toolbar);
        RecyclerView         recyclerView    = findViewById(R.id.city_list);
        FloatingActionButton fab             = findViewById(R.id.fab);
        View                 detailContainer = findViewById(R.id.city_detail_container);

        //Set up my action bar and toolbar details
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //Set click listener on action button
        fab.setOnClickListener(this);

        // The detail container view will be present only in the large-screen layouts
        // (res/values-w900dp) If this view is present, then the activity should be in two-pane mode.
        mIsTablet = detailContainer != null;

        //Set up my adapter
        CityAdapter adapter = new CityAdapter(this, DummyContent.ITEMS, mIsTablet);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, AddCityActivity.class);
        startActivity(intent);
    }


}
