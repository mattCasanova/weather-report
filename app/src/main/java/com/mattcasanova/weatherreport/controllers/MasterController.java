package com.mattcasanova.weatherreport.controllers;

import android.content.SharedPreferences;
import android.view.View;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.Utility.Constants;
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

    private LoadCityIdsTask     loadCitiesTask         = null;
    private MasterViewInterface view;
    private City                recentlyDeletedCity    = null;
    private int                 recentlyDeletedCityPos = -1;

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
        if (view.getCitiesCount() >= Constants.MAX_CITIES) {
           view.displayError(Constants.ADD_CITY_ERROR);
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

    /**
     * The action to take when an item in the view list is swiped
     *
     * @param city The city that was selected
     * @param position The position that was swiped
     */
    public void onListItemSwiped(City city, int position) {
        //Tell the view to delete the city
        view.deleteCityAt(position);

        //Save the city in case we need to undo
        recentlyDeletedCity    = city;
        recentlyDeletedCityPos = position;

        //Tell view to show undo
        view.showUndoSnackBar();
    }

    /**
     * Allows the controller to undo the most recent delete action
     */
    public void undoDeleteCity() {
        if (recentlyDeletedCity != null) {
            view.addCityAt(recentlyDeletedCity, recentlyDeletedCityPos);

            recentlyDeletedCity    = null;
            recentlyDeletedCityPos = -1;
        }
    }

    /**
     * Let contoller be notified when snackbar is done
     */
    public void onSnackbarTimeout() {
        recentlyDeletedCityPos = -1;
        recentlyDeletedCity    = null;
    }

    /**
     * Starts the task to load cities from a given comma separated string
     *
     * @param cityIdsString A comma separated string of city ids to load
     */
    public void loadSavedCities(String cityIdsString) {
        if (loadCitiesTask != null) {
            return;
        }

        loadCitiesTask = new LoadCityIdsTask(cityIdsString, this);
        loadCitiesTask.execute();
    }

    /**
     * This handles the success of the the loadCities API call
     * @param cities The cities that were loaded
     */
    @Override
    public void onSuccess(List<City> cities) {
        loadCitiesTask = null;
        view.loadCities(cities);
    }

    /**
     * Handles the error if one occurs while trying to load the cities
     * @param errorMessage The message to display
     */
    @Override
    public void onError(String errorMessage) {
        loadCitiesTask = null;
        view.displayError(errorMessage);

    }
}
