package com.mattcasanova.weatherreport.activities;

import com.mattcasanova.weatherreport.models.City;

import java.util.List;

public interface AddViewInterface {

    void loadCities(List<City> cities);
    void displayError(String errorMessage);

}
