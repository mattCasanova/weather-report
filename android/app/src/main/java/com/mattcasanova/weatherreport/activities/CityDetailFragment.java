package com.mattcasanova.weatherreport.activities;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.Utility.Alerts;
import com.mattcasanova.weatherreport.Utility.Constants;
import com.mattcasanova.weatherreport.models.City;

/**
 * A fragment representing a single City detail screen.
 * This fragment is either contained in a {@link CityListActivity}
 * in two-pane mode (on tablets) or a {@link CityDetailActivity}
 * on handsets.
 */
public class CityDetailFragment extends Fragment {
    private City city;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityDetailFragment() {
    }

    /**
     *
     */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String CITY_PARAM_KEY = getString(R.string.city_param_key);
        Activity activity     = this.getActivity();
        Bundle bundleArgs     = getArguments();

        // If we don't have an activity we are in trouble.  That means something is wrong and the
        // App will crash
        if (activity == null) {
            return;
        }

        if (bundleArgs == null || !bundleArgs.containsKey(CITY_PARAM_KEY)) {
            activity.finish();
            return;
        }

        city = (City) bundleArgs.getSerializable(CITY_PARAM_KEY);

        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            String title = String.format("%s, %s", city.getName(), city.getCountryCode());
            appBarLayout.setTitle(title);
        }
    }

    /**
     * Builds our detail screen by setting the city data
     * @param inflater used to inflate our views
     * @param container passwed tot he inflater method
     * @param savedInstanceState Not Used
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.city_detail, container, false);

        ImageView icon        = rootView.findViewById(R.id.icon);
        TextView temp         = rootView.findViewById(R.id.temperature);
        TextView description  = rootView.findViewById(R.id.description);
        TextView minTemp      = rootView.findViewById(R.id.min_temperature);
        TextView maxTemp      = rootView.findViewById(R.id.max_temperature);
        TextView pressure     = rootView.findViewById(R.id.pressure);
        TextView humidity     = rootView.findViewById(R.id.humidity);


        Activity activity = getActivity();

        if(activity != null) {
            String iconName = Constants.ICON_PREFIX + city.getIcon();
            int resourceId = activity.getResources().getIdentifier(iconName, Constants.DRAWABLE_TYPE, activity.getPackageName());
            icon.setImageDrawable(activity.getDrawable(resourceId));
        }



        temp.setText(city.getCurrTemperature());
        description.setText(city.getDescription());
        minTemp.setText(city.getMinTemperature());
        maxTemp.setText(city.getMaxTemperature());
        pressure.setText(city.getPressure());
        humidity.setText(city.getHumidity());

        return rootView;
    }
}
