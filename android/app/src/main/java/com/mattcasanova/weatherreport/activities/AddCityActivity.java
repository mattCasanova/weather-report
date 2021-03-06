package com.mattcasanova.weatherreport.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.Utility.Alerts;
import com.mattcasanova.weatherreport.controllers.AddController;
import com.mattcasanova.weatherreport.models.City;

import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, AddViewInterface, OnSuccessListener<Location>, View.OnClickListener {
    private FusedLocationProviderClient locationClient;
    private AddController controller;
    private List<City> cities;
    private NameAdapter nameAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        ActionBar actionBar = getSupportActionBar();
        SearchView searchBar = findViewById(R.id.searchBar);
        RecyclerView recyclerView = findViewById(R.id.name_list);
        Button btnCurrentLoc = findViewById(R.id.current_location);
        progressBar = findViewById(R.id.progress_bar);

        if (actionBar != null) {
            String addTitle = getString(R.string.title_add_city);
            actionBar.setTitle(addTitle);
        }

        btnCurrentLoc.setOnClickListener(this);

        cities = new ArrayList<>();

        //Set up my adapter
        nameAdapter = new NameAdapter();
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nameAdapter);

        progressBar.setVisibility(View.GONE);
        searchBar.setOnQueryTextListener(this);
        controller = new AddController(this);

        locationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * When the submit button on the keyboard is clicked
     * @param s The search string
     * @return This handles the message
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        progressBar.setVisibility(View.VISIBLE);
        controller.search(s);
        return false;
    }

    /**
     * We don't respond to this message because that could use up too many API calls, instead we
     * wait for submit
     *
     * @param s The string
     * @return If this handles the message
     */
    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    /**
     * The Action to take when a city is clicked
     * @param city The city that was selected
     */
    @Override
    public void onCityItemSelected(City city) {
        Intent returnData = new Intent();
        String ADD_CITY_KEY = getString(R.string.add_city_key);
        returnData.putExtra(ADD_CITY_KEY, city);
        setResult(RESULT_OK, returnData);
        finish();
    }

    @Override
    public void loadCities(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        this.nameAdapter.notifyDataSetChanged();

        this.progressBar.setVisibility(View.GONE);

    }

    /**
     * Displays an error/Alert message to the user.
     * @param errorMessage The message to show.
     */
    @Override
    public void displayError(String errorMessage) {
        this.cities.clear();
        this.progressBar.setVisibility(View.GONE);
        String title = getString(R.string.title_error);
        String buttonTitle = getString(R.string.button_ok);
        Alerts.NoOptionAlert(title, errorMessage, buttonTitle, this);
    }

    /**
     * On Success of getting the last known location
     * @param loc The last known location of the d
     */
    @Override
    public void onSuccess(Location loc) {
        if (loc != null) {
            controller.getLocation(loc.getLatitude(), loc.getLongitude());

        }
        else {
            String title       = getString(R.string.title_error);
            String message     = getString(R.string.error_last_loc);
            String buttonTitle = getString(R.string.button_ok);
            Alerts.NoOptionAlert(title, message, buttonTitle, this);
        }
    }

    /**
     * Method to respond to buttons clicks in this activity
     * @param view The view that was clicked
     */
    @Override
    public void onClick(View view) {
        getLastLocation();
    }

    /**
     * Checks the results of our premission request
     * @param requestCode The request code we send
     * @param permissions The list of permissions we requested
     * @param grantResults The list of results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode != getResources().getInteger(R.integer.fine_permission_request))
            return;

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        }

    }

    /**
     * Method to check permission and get last location from location services
     *
     */
    private void getLastLocation() {
        //Check permissions to get access to location services
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //We can only request permissions if we have the right API level
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, getResources().getInteger(R.integer.fine_permission_request));
            } else {
                String title       = getString(R.string.title_error);
                String message     = getString(R.string.error_no_permission_api);
                String buttonTitle = getString(R.string.button_ok);
                Alerts.NoOptionAlert(title, message, buttonTitle, this);
            }
            return;
        }
        locationClient.getLastLocation().addOnSuccessListener(this);
    }

    /**
     * NameAdapter for the AddActivity RecycleView.  These adapters could be factored out into separate
     * classes but they are often uses exclusively for a specific Activity.  By doing it this way, we don't need
     * to maintain a list in both the view and adapter.
     */
    private class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameViewHolder > {


        /**
         * Adapter method to create our ViewHolder
         *
         * @param parent The parent view so we can inflate our layout
         * @param viewType We only have one cell type, so we don't need this
         * @return Our newly created NameViewHolder
         */
        @NonNull
        @Override
        public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View nameListItem       = inflater.inflate(R.layout.name_list_content, parent, false);
            return  new NameViewHolder(nameListItem);
        }

        /**
         * Adapter method to set our ViewHolder Data
         *
         * @param holder The holder that needs data
         * @param position The position in the recycler view that was clicked
         */
        @Override
        public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
            City city   = cities.get(position);
            String name = String.format("%s, %s", city.getName(), city.getCountryCode());
            holder.tvName.setText(name);

        }
        /**
         * Adapter method to specify how many cells we need
         * @return The Number of cells
         */
        @Override
        public int getItemCount() {
            return cities.size();
        }

        /**
         * The view holder for the name recycler view
         */
        class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvName;

            /**
             * We pass in the ViewGroup parent so we can allow the view holder to respond to its own click
             *
             * @param view The View of this cell layout
             */
            NameViewHolder(View view) {
                super(view);
                tvName           = view.findViewById(R.id.city_name);
                ViewGroup parent = view.findViewById(R.id.root);
                parent.setOnClickListener(this);
            }

            /**
             * We allow the viewholder to handle the click because it knows its own position
             * @param view The view that was clicked
             *
             */
            @Override
            public void onClick(View view) {
                //The view holder knows its own position in the list
                City clickedCity = cities.get(getAdapterPosition());
                controller.onListItemClicked(clickedCity);
            }
        }

    }

}
