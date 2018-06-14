package com.mattcasanova.weatherreport.activities;

import com.mattcasanova.weatherreport.models.City;

import java.util.List;

public interface MasterViewInterface {
    void goToDetail(City city);
    void goToAddCity();
    void loadCities(List<City> cities);
    void addCity(City city);
    void displayError(String errorMessage);

    boolean doesCityExist(City newCity);
    int getCitiesCount();
}
