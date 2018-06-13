package com.mattcasanova.weatherreport.tasks;

import android.os.AsyncTask;

import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;

import java.util.List;

public abstract class BaseTask extends AsyncTask<Void, String, List<City> > {
    protected static final String API_PREFIX    = "https://api.openweathermap.org/data/2.5/";
    protected static final String API_SUFFIX    = "&units=imperial&APPID=ffbf145fadb301907b1b8f356e6b91bc";

    protected static final String SEARCH_PREFIX = "find?q=";
    protected static final String SEARCH_SUFFIX = "&type=like";

    protected String errorString = "";
    protected OnTaskResult listener;

    BaseTask(OnTaskResult listener) {
        this.listener = listener;
    }


}
