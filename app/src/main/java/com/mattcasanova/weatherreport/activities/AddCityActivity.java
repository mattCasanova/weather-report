package com.mattcasanova.weatherreport.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.mattcasanova.weatherreport.R;

public class AddCityActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        ActionBar actionBar  = getSupportActionBar();
        SearchView searchBar = findViewById(R.id.searchBar);


        if(actionBar != null) {
            String addTitle = getString(R.string.title_add_city);
            actionBar.setTitle(addTitle);
        }

        searchBar.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

}
