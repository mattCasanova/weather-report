package com.mattcasanova.weatherreport.tasks;


import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;

import java.util.List;

public class GetSearchResultTask extends BaseGetCitiesTask {
    public GetSearchResultTask(String searchString, OnTaskResult listener) {
        super(listener);
        //Create my api call based on my search string
        this.queryString = API_PREFIX + SEARCH_PREFIX + searchString + SEARCH_SUFFIX + API_SUFFIX;
    }


    /**
     * We need to make sure we let the user know that there were no search results.
     * @param cities The list of cities retrieved from the task.
     */
    @Override
    protected void onPostExecute(List<City> cities) {
        super.onPostExecute(cities);

        if(!errorString.isEmpty()) {
            listener.onError(errorString);
        }
        else if (cities.isEmpty()) {
            listener.onError(SEARCH_ALERT);
        }
        else {
            listener.onSuccess(cities);
        }

    }
}
