package com.rn.travelcraft.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rn.travelcraft.R;
import com.rn.travelcraft.adapters.CharityAdapter;
import com.rn.travelcraft.adapters.CharityAdapter.RecyclerViewClickListener;
import com.rn.travelcraft.application.TravelCraftApp;
import com.rn.travelcraft.parseData.Charity;
import com.rn.travelcraft.utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizationListActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private static final String TAG = OrganizationListActivity.class.getSimpleName();

    public static final String SELECTED_CHARITY = "Selected Charity";
    public static final String SELECTED_CHARITY_ID = "Selected Charity ID";

    final List<Charity> mCharityData = new ArrayList<>();
    private HashMap<Charity, String> charityIDs = new HashMap<>();

    private RecyclerView mRecyclerView;
    private CharityAdapter mCharityAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mProgressView;
    private Utils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_list);
        Log.d(TravelCraftApp.TAG, "OrganizationListActivity onCreate()");

        mUtils = new Utils(OrganizationListActivity.this);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Browse");*/

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.listings_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayList();
            }
        });

        mProgressView = findViewById(R.id.progress);

        displayList();
    }

    public void displayList() {
        if (!mCharityData.isEmpty()) {
            mCharityData.clear();
        }

        if (!mSwipeRefreshLayout.isRefreshing()) {
            mUtils.showProgress(mProgressView, true);
        }

        ParseQuery<ParseObject> charityQuery = new ParseQuery<>("Charities");
        charityQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectList, ParseException e) {

                if (e == null) {
                    for (final ParseObject charity : objectList) {
                        final Charity currentCharity = new Charity();
                        currentCharity.setLogo(charity.getParseFile("logo"));
                        currentCharity.setName(charity.getString("name"));
                        currentCharity.setDescription(charity.getString("description"));
                        currentCharity.setBackground(charity.getParseFile("background"));

                        mCharityData.add(currentCharity);
                        charityIDs.put(currentCharity, charity.getObjectId());
                    }
                } else {
                    Log.d(TravelCraftApp.TAG, "CharityQuery error: " + e);
                }
                mRecyclerView = (RecyclerView) findViewById(R.id.rv_charity);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(OrganizationListActivity.this));
                mCharityAdapter = new CharityAdapter(OrganizationListActivity.this,
                                                    mCharityData, OrganizationListActivity.this);
                mRecyclerView.setAdapter(mCharityAdapter);

                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mUtils.showProgress(mProgressView, false);
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TravelCraftApp.TAG, "OrganizationListActivity onDestroy()");
    }

    // TODO: might not work for > 3?
    @Override
    public void recyclerViewListClicked(View v, int position) {
        String charitySelected = mCharityData.get(position).getName();

        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra(SELECTED_CHARITY, charitySelected);
        intent.putExtra(SELECTED_CHARITY_ID, charityIDs.get(mCharityData.get(position)));

        //TODO: shared element transition

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
