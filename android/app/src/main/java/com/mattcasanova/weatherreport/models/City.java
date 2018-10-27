package com.mattcasanova.weatherreport.models;


import com.mattcasanova.weatherreport.Utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private String id;
    private String countryCode;
    private String humidity;
    private String pressure;
    private String currTemperature;
    private String minTemperature;
    private String maxTemperature;
    private String description;
    private String icon;

    /**
     * Creates a city from a passed on JSONObject
     * @param jsonCity The json data to load
     * @throws JSONException Throws if the required data isn't in the json object
     */
    public City(JSONObject jsonCity) throws JSONException {
        //Get Top Level Items
        name               = jsonCity.getString(Constants.NAME);
        id                 = jsonCity.getString(Constants.ID);

        //Get my Sys Level Items
        JSONObject sys     = jsonCity.getJSONObject(Constants.SYSTEM);
        countryCode        = sys.getString(Constants.COUNTRY_CODE);

        //Get my Main Level items
        JSONObject main    = jsonCity.getJSONObject(Constants.MAIN);
        humidity           = main.getString(Constants.HUMIDITY) + Constants.HUMIDITY_SUFFIX;
        pressure           = main.getString(Constants.PRESSURE) + Constants.PRESSURE_SUFFIX;
        currTemperature    = main.getString(Constants.TEMP)     + Constants.FAHRENHEIT_SYMBOL;
        minTemperature     = main.getString(Constants.TEMP_MIN) + Constants.FAHRENHEIT_SYMBOL;
        maxTemperature     = main.getString(Constants.TEMP_MAX) + Constants.FAHRENHEIT_SYMBOL;

        //Get The Weather Level array/items
        JSONArray weather = jsonCity.getJSONArray(Constants.WEATHER);

        // We could/should grab all of the weather data, but we are just going to grab the
        // First one for this project
        if (weather.length() >= 0 ) {
            description        = weather.getJSONObject(0).getString(Constants.DESCRIPTION);
            icon               = weather.getJSONObject(0).getString(Constants.ICON);
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getCurrTemperature() {
        return currTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }



}