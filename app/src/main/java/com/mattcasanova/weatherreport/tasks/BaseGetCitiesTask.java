package com.mattcasanova.weatherreport.tasks;

import android.os.AsyncTask;

import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Base Task for getting a list of cities from the API.  This is used when reading the in saved
 * cities as well as searching for cities.
 */
abstract class BaseGetCitiesTask extends AsyncTask<Void, String, List<City> > {
    //API CONSTANTS
    static final String API_PREFIX            = "https://api.openweathermap.org/data/2.5/";
    static final String API_SUFFIX            = "&units=imperial&APPID=ffbf145fadb301907b1b8f356e6b91bc";
    static final String SEARCH_PREFIX         = "find?q=";
    static final String SEARCH_SUFFIX         = "&type=like";

    //JSON CONSTANTS
    private static final int    CODE_GOOD     = 200;
    private static final String RESULT_CODE   = "cod";
    private static final String COUNT         = "count";
    private static final String LIST          = "list";

    private static final String URL_ERROR     = "Your search string is invalid, please use only letters and numbers";
    private static final String IO_ERROR      = "There was a problem with the connection.  Please try again.";
    private static final String JSON_ERROR    = "The data returned couldn't be read properly.  Please try again.";

    static final String SEARCH_ALERT          = "No city data could be found.  Try another search";



    String errorString = "";
    String queryString = "";
    OnTaskResult listener;


    BaseGetCitiesTask(OnTaskResult listener) {
        this.listener = listener;
    }

    /**
     * This task makes a connection based of the the query string and parses returned list of
     * cities.
     *
     */
    @Override
    protected List<City> doInBackground(Void... voids) {
        List<City> cities     = new ArrayList<>();


        try {
            //Create my connection and load the results
            URL url                      = new URL(queryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream           = connection.getInputStream();
            BufferedReader reader        = new BufferedReader(new InputStreamReader(stream));
            StringBuilder resultBuilder  = new StringBuilder();

            //Read my results line by line
            String line                  = reader.readLine();

            while(line != null) {
                resultBuilder.append(line);
                line = reader.readLine();
            }

            //Check to make sure we got a good result from the API
            JSONObject root = new JSONObject(resultBuilder.toString());
            if (root.has(RESULT_CODE) && root.getInt(RESULT_CODE) != CODE_GOOD) {
                return cities;
            }

            //Then get our result list and count
            int count          = root.getInt(COUNT);
            JSONArray jsonList = root.getJSONArray(LIST);

            //Get each json city and let the city object handle the parsing
            for(int i = 0; i < count; ++i) {
                JSONObject jsonCity = jsonList.getJSONObject(i);
                cities.add(new City(jsonCity));
            }

            return cities;

        } catch (MalformedURLException e) {
            errorString = URL_ERROR;
            e.printStackTrace();
        } catch (IOException e) {
            errorString = IO_ERROR;
            e.printStackTrace();
        } catch (JSONException e) {
            errorString = JSON_ERROR;
            e.printStackTrace();
        }

        cities.clear();
        return cities;
    }


}
