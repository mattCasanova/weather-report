package com.mattcasanova.weatherreport.tasks;

import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;

import java.util.List;

public class LoadCityIdsTask extends BaseGetCitiesTask {

    public LoadCityIdsTask(String cityIdsString, OnTaskResult listener) {
        super(listener);
        this.queryString = API_PREFIX + LOAD_GROUP_PREFIX + cityIdsString + API_SUFFIX;
    }

}
