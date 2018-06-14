package com.mattcasanova.weatherreport.controllers;

import android.content.SharedPreferences;
import android.view.View;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.activities.MasterViewInterface;
import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;
import com.mattcasanova.weatherreport.tasks.LoadCityIdsTask;

import java.util.List;

/**
 * This class is the controller between our data set and our master view.  For this project, we
 * don't have much of a back end to deal with, just and API to get from, but this will prevent logic
 * from cluttering up the views.
 */
public class MasterController implements OnTaskResult {
    private static final String ADD_CITY_ERROR = "Due to limitations on how many API requests can be made in a given time frame, the number of cities on the dashboard has been limited.";

    private static final int MAX_CITIES = 5;

    private LoadCityIdsTask loadCitiesTask = null;

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
        if (view.getCitiesCount() >= MAX_CITIES) {
           view.displayError(ADD_CITY_ERROR);
           return;
        }

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
        if (city == null || view.doesCityExist(city))
            return;

        this.view.addCity(city);
    }

    public void loadSavedCities(String cityIdsString) {
        if (loadCitiesTask != null) {
            return;
        }

        loadCitiesTask = new LoadCityIdsTask(cityIdsString, this);
        loadCitiesTask.execute();
    }

    @Override
    public void onSuccess(List<City> cities) {
        loadCitiesTask = null;
        view.loadCities(cities);
    }

    @Override
    public void onError(String errorMessage) {
        loadCitiesTask = null;
        view.displayError(errorMessage);

    }
}
