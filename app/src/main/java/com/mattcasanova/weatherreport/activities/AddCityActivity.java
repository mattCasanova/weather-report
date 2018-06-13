package com.mattcasanova.weatherreport.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mattcasanova.weatherreport.R;

public class AddCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            String addTitle = getString(R.string.title_add_city);
            actionBar.setTitle(addTitle);
        }
    }
}
