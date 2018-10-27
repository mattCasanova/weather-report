package com.mattcasanova.weatherreport.controllers;

import com.mattcasanova.weatherreport.activities.AddViewInterface;
import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;
import com.mattcasanova.weatherreport.tasks.GetLocationResultTask;
import com.mattcasanova.weatherreport.tasks.GetSearchResultTask;

import java.util.List;

public class AddController implements OnTaskResult {

    private GetSearchResultTask getSearchTask     = null;
    private GetLocationResultTask getLocationTask = null;
    private AddViewInterface view;

    /**
     *
     * @param view The view to control
     */
    public AddController(AddViewInterface view) {
        this.view = view;
    }

    /**
     * Performs an API search for the selected string if there isn't already a search going
     * @param searchString The string to search for
     */
    public void search(String searchString) {
        //Only allow one search at a time
        if (getSearchTask != null || getLocationTask != null) {
            return;
        }

        getSearchTask = new GetSearchResultTask(searchString, this);
        getSearchTask.execute();

    }

    /**
     * Get the location from our api
     * @param lat the latitude to search for
     * @param lon the longitude to search for
     */
    public void getLocation(double lat, double lon) {
        //Only allow one search at a time
        if (getSearchTask != null || getLocationTask != null) {
            return;
        }

        getLocationTask = new GetLocationResultTask(lat, lon, this);
        getLocationTask.execute();
    }


    public void onListItemClicked(City city) {
        view.onCityItemSelected(city);
    }

    /**
     * Success responder for the search
     * @param cities The city results from the search
     */
    @Override
    public void onSuccess(List<City> cities) {
        getSearchTask = null;
        getLocationTask = null;
        view.loadCities(cities);
    }

    /**
     * Error responder for the search
     * @param errorMessage The error message
     */
    @Override
    public void onError(String errorMessage) {
        getSearchTask = null;
        getLocationTask = null;
        view.displayError(errorMessage);
    }
}
