package com.mattcasanova.weatherreport.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;
import com.mattcasanova.weatherreport.tasks.GetSearchResultTask;

import java.util.List;

public class AddCityActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, OnTaskResult{
    private GetSearchResultTask getSearchResults = null;
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
        getSearchResults = new GetSearchResultTask(s, this);
        getSearchResults.execute();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onSuccess(List<City> cities) {

    }

    @Override
    public void onError(String errorMessage) {

    }
}
