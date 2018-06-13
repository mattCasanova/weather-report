package com.mattcasanova.weatherreport.adaptors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.activities.CityDetailActivity;
import com.mattcasanova.weatherreport.activities.CityDetailFragment;
import com.mattcasanova.weatherreport.activities.CityListActivity;
import com.mattcasanova.weatherreport.dummy.DummyContent;

import java.util.List;


/**
 * CityAdapter for the CityListActivity RecycleView
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> implements View.OnClickListener{
    private CityListActivity             mParentActivity;
    private List<DummyContent.DummyItem> mCities;
    private boolean                      mIsTablet;

    /**
     * Constructor for the City Adapter
     * @param parent The parent class that holds the recycle view
     * @param cities The cities data that we want to display
     * @param isTwoPane True if we show master detail side by side, false if we nav to another screen
     */
    public CityAdapter(CityListActivity parent, List<DummyContent.DummyItem> cities, boolean isTwoPane) {
        mCities         = cities;
        mParentActivity = parent;
        mIsTablet       = isTwoPane;
    }

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
        return new CityViewHolder(cityListItem);
    }

    /**
     * Adapter method to set our ViewHolder Data
     *
     * @param holder The holder that needs data
     * @param position The position in the recycler view that was clicked
     */
    @Override
    public void onBindViewHolder(final CityViewHolder holder, int position) {
        DummyContent.DummyItem city = mCities.get(position);
        holder.mIdView.setText(city.id);
        holder.mContentView.setText(city.content);

        holder.itemView.setTag(city);
        holder.itemView.setOnClickListener(this);
    }

    /**
     * Adapter method to specify how many cells we need
     * @return The Number of cells
     */
    @Override
    public int getItemCount() {
        return mCities.size();
    }

    /**
     * The action to take when a ViewHolder is clicked
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        String CITY_PARAM_KEY       = mParentActivity.getString(R.string.city_param_key);
        DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
        if (mIsTablet) {
            CityDetailFragment fragment = new CityDetailFragment();
            Bundle arguments            = new Bundle();

            arguments.putString(CITY_PARAM_KEY, item.id);
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.city_detail_container, fragment)
                    .commit();
        } else {
            Context context = view.getContext();
            Intent intent   = new Intent(context, CityDetailActivity.class);
            intent.putExtra(CITY_PARAM_KEY, item.id);

            context.startActivity(intent);
        }
    }

    /**
     * The view holder for the City recycler view
     */
    class CityViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        CityViewHolder(View view) {
            super(view);
            mIdView      = view.findViewById(R.id.id_text);
            mContentView = view.findViewById(R.id.content);
        }
    }
}