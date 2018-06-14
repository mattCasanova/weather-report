package com.mattcasanova.weatherreport.controllers;

import android.view.View;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.activities.MasterViewInterface;
import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;

import java.util.List;

/**
 * This class is the controller between our data set and our master view.  For this project, we
 * don't have much of a back end to deal with, just and API to get from, but this will prevent logic
 * from cluttering up the views.
 */
public class MasterController implements OnTaskResult {
    private MasterViewInterface view;

    /**
     *
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

    /**
     * Adds a single new city to the view, it could also do something on our backend but we don't have
     * one for this project
     *
     * @param city The city to add
     */
    public void addNewCity(City city) {
        if (city != null) {
            this.view.addCity(city);
        }
    }

    @Override
    public void onSuccess(List<City> cities) {

    }

    @Override
    public void onError(String errorMessage) {

    }
}
