package com.mattcasanova.weatherreport.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class City implements Serializable {
    private static final String NAME         = "name";
    private static final String ID           = "id";
    private static final String COUNTRY_CODE = "country";
    private static final String HUMIDITY     = "humidity";
    private static final String PRESSURE     = "pressure";
    private static final String TEMP         = "temp";
    private static final String TEMP_MIN     = "temp_min";
    private static final String TEMP_MAX     = "temp_max";
    private static final String ICON         = "icon";
    private static final String DESCRIPTION  = "description";
    private static final String SYSTEM       = "sys";
    private static final String MAIN         = "main";
    private static final String WEATHER      = "weather";

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

    /**
     * Creates a city from a passed on JSONObject
     * @param jsonCity The json data to load
     * @throws JSONException Throws if the required data isn't in the json object
     */
    public City(JSONObject jsonCity) throws JSONException {
        //Get Top Level Items
        name               = jsonCity.getString(NAME);
        id                 = jsonCity.getString(ID);

        //Get my Sys Level Items
        JSONObject sys     = jsonCity.getJSONObject(SYSTEM);
        countryCode        = sys.getString(COUNTRY_CODE);

        //Get my Main Level items
        JSONObject main    = jsonCity.getJSONObject(MAIN);
        humidity           = main.getString(HUMIDITY);
        pressure           = main.getString(PRESSURE);
        currTemperature    = main.getString(TEMP);
        minTemperature     = main.getString(TEMP_MIN);
        maxTemperature     = main.getString(TEMP_MAX);

        //Get The Weather Level array/items
        JSONArray weather = jsonCity.getJSONArray(WEATHER);

        // We could/should grab all of the weather data, but we are just going to grab the
        // First one for this project
        if (weather.length() >= 0 ) {
            description        = weather.getJSONObject(0).getString(DESCRIPTION);
            icon               = weather.getJSONObject(0).getString(ICON);
        }
    }



}