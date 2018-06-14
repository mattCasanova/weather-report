package com.mattcasanova.weatherreport.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.controllers.MasterController;
import com.mattcasanova.weatherreport.models.City;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Cities. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CityDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CityListActivity extends AppCompatActivity implements MasterViewInterface,  View.OnClickListener {
    private boolean          isTwoPaned;
    private List<City>       cities = new ArrayList<>();
    private MasterController controller;
    private CityAdapter      cityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        //Get References to my views
        Toolbar toolbar                 = findViewById(R.id.toolbar);
        View detailContainer            = findViewById(R.id.city_detail_container);
        RecyclerView recyclerView       = findViewById(R.id.city_list);
        FloatingActionButton fabAddCity = findViewById(R.id.fab_add_city);

        //Set up my action bar and toolbar details
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //Set click listener on action button
        fabAddCity.setOnClickListener(this);

        // The detail container view will be present only in the large-screen layouts
        // (res/values-w900dp) If this view is present, then the activity should be in two-pane mode.
        isTwoPaned = detailContainer != null;



        //Set up my adapter
        cityAdapter = new CityAdapter();
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdapter);

        controller = new MasterController(this);
    }

    /**
     * Pass the clicked view to our controller, and it will decide how to respond
     *
     * @param view The View that was clicked
     */
    @Override
    public void onClick(View view) {
        controller.onButtonClicked(view);
    }

    @Override
    public void goToDetail(City city) {
        String CITY_PARAM_KEY = getString(R.string.city_param_key);

        if (isTwoPaned) {
            CityDetailFragment fragment = new CityDetailFragment();
            Bundle arguments            = new Bundle();

            arguments.putString(CITY_PARAM_KEY, city.id);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.city_detail_container, fragment)
                    .commit();
        } else {
            Intent intent   = new Intent(this, CityDetailActivity.class);
            intent.putExtra(CITY_PARAM_KEY, city.id);
            startActivity(intent);
        }
    }

    /**
     * Starts the AddCity Activity and waits for the result
     */
    @Override
    public void goToAddCity() {
        Intent intent = new Intent(this, AddCityActivity.class);
        startActivityForResult(intent, getResources().getInteger(R.integer.add_request));
    }

    /**
     * Loads a fresh set of cities in the recycle view
     * @param cities The new cities to load
     */
    @Override
    public void loadCities(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        this.cityAdapter.notifyDataSetChanged();
    }

    /**
     * Adds a city the recycler view and updates the adapter
     * @param city The new city to add
     */
    @Override
    public void addCity(City city) {
        cities.add(city);
        int endPosition = cities.size();
        cityAdapter.notifyItemInserted(endPosition);

    }

    /**
     * Gets the result of the Activity
     *
     * @param requestCode  The request code that started the activity
     * @param resultCode The Result of the activity
     * @param data The data passed back from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == getResources().getInteger(R.integer.add_request) && resultCode == RESULT_OK && data != null) {
            String ADD_CITY_KEY = getString(R.string.add_city_key);
            City city = (City) data.getSerializableExtra(ADD_CITY_KEY);
            controller.addNewCity(city);
            Log.d("Task", city.name);
        }
    }

    /**
     * CityAdapter for the CityListActivity RecycleView.  These adapters could be factored out into seperate
     * classes but they are often uses exclusively for a specific Activity.  By doing it this way, we don't need
     * to maintain a list in both the view and adapter.
     */
    private class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

        /**
         * Adapter method to create our ViewHolder
         *
         * @param parent The parent view so we can inflate our layout
         * @param viewType We only have one cell type, so we don't need this
         * @return Our newly created CityViewHolder
         */
        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View cityListItem       = inflater.inflate(R.layout.city_list_content, parent, false);
            return new CityViewHolder(cityListItem, parent);
        }

        /**
         * Adapter method to set our ViewHolder Data
         *
         * @param holder The holder that needs data
         * @param position The position in the recycler view that was clicked
         */
        @Override
        public void onBindViewHolder(final CityViewHolder holder, int position) {
            City city = cities.get(position);
            holder.tvID.setText(city.id);
            holder.tvContent.setText(city.name);
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
         * The view holder for the City recycler view
         */
        class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvID;
            TextView tvContent;

            /**
             * We pass in the ViewGroup parent so we can allow the view holder to respond to its own click
             *
             * @param view The View of this cell layout
             * @param parent The parent view of this cell
             */
            CityViewHolder(View view, ViewGroup parent) {
                super(view);
                tvID      = view.findViewById(R.id.id_text);
                tvContent = view.findViewById(R.id.content);
                parent.setOnClickListener(this);
            }

            /**
             * We let he view hold respond because it knows its position in the list
             * @param view The view that was clicked.  We don't need it
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
