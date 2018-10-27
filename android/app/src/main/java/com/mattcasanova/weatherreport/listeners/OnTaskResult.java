package com.mattcasanova.weatherreport.listeners;

import com.mattcasanova.weatherreport.models.City;

import java.util.List;

public interface OnTaskResult {
    public void onSuccess(List<City> cities);
    public void onError(String errorMessage);
}
