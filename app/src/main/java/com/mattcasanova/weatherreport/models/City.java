package com.mattcasanova.weatherreport.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class City implements Serializable {
    public String name            = "";
    public String id              = "";
    public String countryCode     = "";
    public String humidity        = "";
    public String pressure        = "";
    public String currTemperature = "";
    public String minTemperature  = "";
    public String maxTemperature  = "";
    public String description     = "";
    public String icon            = "";

    /**
     * Default constructor
     */
    public City() {}

    public City(JSONObject jsonCity) throws JSONException {
        name               = jsonCity.getString("name");
        id                 = jsonCity.getString("id");

        JSONObject sys     = jsonCity.getJSONObject("sys");
        countryCode        = sys.getString("country");

        JSONObject main    = jsonCity.getJSONObject("main");
        humidity           = main.getString("humidity");
        pressure           = main.getString("pressure");
        currTemperature    = main.getString("temp");
        minTemperature     = main.getString("temp_min");
        maxTemperature     = main.getString("temp_max");

        JSONArray weather = jsonCity.getJSONArray("weather");

        // We could/should grab all of the weather data, but we are just going to grab the
        // First one for this project
        if (weather.length() >= 0 ) {
            description        = weather.getJSONObject(0).getString("description");
            icon               = weather.getJSONObject(0).getString("icon");
        }
    }



}