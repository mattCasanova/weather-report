package com.mattcasanova.weatherreport.tasks;

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
import java.util.Locale;

public class GetLocationResultTask extends BaseGetCitiesTask {
    public GetLocationResultTask(double lat, double lon, OnTaskResult listener) {
        super(listener);
        String coordString = String.format(Locale.US, "lat=%f&lon=%f", lat, lon);
        queryString = API_PREFIX + LAT_LONG_PREFIX + coordString + API_SUFFIX;
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
                errorString = API_ERROR;
                return cities;
            }

            cities.add(new City(root));
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
