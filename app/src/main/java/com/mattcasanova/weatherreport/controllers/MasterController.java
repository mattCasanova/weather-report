package com.mattcasanova.weatherreport.controllers;

import android.view.View;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.activities.MasterViewInterface;
import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;

import java.util.List;


public class MasterController implements OnTaskResult {
    private MasterViewInterface view;

    /**
     * Connect the view and data source
     *
     * @param view The MasterView to Control
     */
    public MasterController(MasterViewInterface view) {
        this.view = view;
    }

    public void onListItemClicked(City city) {
        view.goToDetail(city);
    }

    /**
     * We let the controller decide what action to take on a button click
     *
     * @param button The view that was clicked
     */
    public void onButtonClicked(View button) {
        if (button.getId() == R.id.fab_add_city) {
            view.goToAddCity();
        }
    }

    @Override
    public void onSuccess(List<City> cities) {

    }

    @Override
    public void onError(String errorMessage) {

    }
}
