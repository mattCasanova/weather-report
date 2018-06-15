package com.mattcasanova.weatherreport.controllers;

import com.mattcasanova.weatherreport.activities.AddViewInterface;
import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;
import com.mattcasanova.weatherreport.tasks.GetSearchResultTask;

import java.util.List;

public class AddController implements OnTaskResult {

    private GetSearchResultTask getSearchResult = null;
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
        if (getSearchResult != null) {
            return;
        }

        getSearchResult = new GetSearchResultTask(searchString, this);
        getSearchResult.execute();

    }

    /**
     *
     */
    public void addCurrentLocation() {

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
        getSearchResult = null;
        view.loadCities(cities);
    }

    /**
     * Error responder for the search
     * @param errorMessage The error message
     */
    @Override
    public void onError(String errorMessage) {
        getSearchResult = null;
        view.displayError(errorMessage);
    }
}
