package com.mattcasanova.weatherreport.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.controllers.AddController;
import com.mattcasanova.weatherreport.dummy.DummyContent;
import com.mattcasanova.weatherreport.listeners.OnTaskResult;
import com.mattcasanova.weatherreport.models.City;
import com.mattcasanova.weatherreport.tasks.GetSearchResultTask;

import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, AddViewInterface{
    private AddController controller;
    private List<City>    cities;
    private NameAdapter   nameAdapter;
    private ProgressBar   progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        ActionBar actionBar       = getSupportActionBar();
        SearchView searchBar      = findViewById(R.id.searchBar);
        RecyclerView recyclerView = findViewById(R.id.name_list);
        progressBar               = findViewById(R.id.progress_bar);

        if(actionBar != null) {
            String addTitle = getString(R.string.title_add_city);
            actionBar.setTitle(addTitle);
        }


        cities = new ArrayList<>();

        //Set up my adapter
        nameAdapter                            = new NameAdapter();
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nameAdapter);

        progressBar.setVisibility(View.INVISIBLE);
        searchBar.setOnQueryTextListener(this);
        controller = new AddController(this);
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
     * @return  If this handles the message
     */
    @Override public boolean onQueryTextChange(String s) { return false; }


    @Override
    public void loadCities(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        this.nameAdapter.notifyDataSetChanged();

        this.progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void displayError(String errorMessage) {
        this.progressBar.setVisibility(View.INVISIBLE);
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
            return  new NameViewHolder(nameListItem, parent);
        }

        /**
         * Adapter method to set our ViewHolder Data
         *
         * @param holder The holder that needs data
         * @param position The position in the recycler view that was clicked
         */
        @Override
        public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
            City city = cities.get(position);
            String name = String.format("%s, %s", city.name, city.countryCode);
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
             * @param parent The parent view of this cell
             */
            NameViewHolder(View view, ViewGroup parent) {
                super(view);
                tvName = view.findViewById(R.id.city_name);
                parent.setOnClickListener(this);
            }

            /**
             * We allow the viewholder to handle the click because it knows its own position
             * @param view
             */
            @Override
            public void onClick(View view) {

            }
        }

    }

}
