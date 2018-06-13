package com.mattcasanova.weatherreport.activities;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattcasanova.weatherreport.R;
import com.mattcasanova.weatherreport.dummy.DummyContent;

/**
 * A fragment representing a single City detail screen.
 * This fragment is either contained in a {@link CityListActivity}
 * in two-pane mode (on tablets) or a {@link CityDetailActivity}
 * on handsets.
 */
public class CityDetailFragment extends Fragment {
    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

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
        final String CITY_PARAM_KEY = getString(R.string.city_param_key);

        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.

        Bundle bundleArgs = getArguments();

        if (bundleArgs.containsKey(CITY_PARAM_KEY)) {
            final String itemId                  = bundleArgs.getString(CITY_PARAM_KEY);
            mItem                                = DummyContent.ITEM_MAP.get(itemId);
            Activity activity                    = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.city_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            TextView tvDetails = rootView.findViewById(R.id.city_detail);
            tvDetails.setText(mItem.details);
        }

        return rootView;
    }
}
