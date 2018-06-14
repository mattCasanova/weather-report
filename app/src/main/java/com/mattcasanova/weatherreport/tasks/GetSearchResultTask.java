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

public class GetSearchResultTask extends BaseTask{
    private String       searchString;

    public GetSearchResultTask(String searchString, OnTaskResult listener) {
        super(listener);
        this.searchString = searchString;
    }

    @Override
    protected List<City> doInBackground(Void... voids) {
        List<City> cities     = new ArrayList<>();

        //Create my api call based on my search string
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(API_PREFIX).append(SEARCH_PREFIX).append(searchString).append(SEARCH_SUFFIX).append(API_SUFFIX);

        try {
            //Create my connection and load the results
            URL url                      = new URL(urlBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream           = connection.getInputStream();
            BufferedReader reader        = new BufferedReader(new InputStreamReader(stream));
            StringBuilder resultBuilder  = new StringBuilder();

            //Read my results line by line
            String line                 = reader.readLine();

            while(line != null) {
                resultBuilder.append(line);
                line = reader.readLine();
            }

            JSONObject root = new JSONObject(resultBuilder.toString());
            if (root.has("cod")) {
                int code = root.getInt("cod");
                if (code != 200) {
                    return cities;
                }
            }
            int count = root.getInt("count");

            JSONArray jsonList = root.getJSONArray("list");

            for(int i = 0; i < count; ++i) {
                JSONObject jsonCity = jsonList.getJSONObject(i);

                City city = new City(jsonCity);
                cities.add(city);
            }

            return cities;

        } catch (MalformedURLException e) {
            errorString = "Your search string is invalid, please use only letters and numbers";
            e.printStackTrace();
        } catch (IOException e) {
            errorString = "There was a problem reading the weather data.  Please try again.";
            e.printStackTrace();
        } catch (JSONException e) {
            errorString = "The Data returned   Please try again.";
            e.printStackTrace();
        }

        cities.clear();
        return cities;
    }

    @Override
    protected void onPostExecute(List<City> cities) {
        super.onPostExecute(cities);

        if(!errorString.isEmpty()) {
            listener.onError(errorString);
        }
        else if (cities.isEmpty()) {
            listener.onError("No Search results could be found");
        }
        else {
            listener.onSuccess(cities);
        }

    }
}
