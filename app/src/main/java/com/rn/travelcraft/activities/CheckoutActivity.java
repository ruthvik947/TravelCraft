package com.rn.travelcraft.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rn.travelcraft.R;
import com.rn.travelcraft.application.TravelCraftApp;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mCartSize;
    private int mCount = 0;
    private int mPrice = 0;
    private JSONArray mCartArray;
    private List<ParseObject> mProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout");

        mDrawer = (DrawerLayout) findViewById(R.id.parent);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawer.setDrawerListener(mDrawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        setupDrawerContent(navigationView);

        mProductList = new ArrayList<>();

        try {
            populateItems();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateItems() throws JSONException {
        mCartArray = new JSONArray();
        mCartArray = ParseUser.getCurrentUser().getJSONArray("cart");
        mCartSize = mCartArray.length();
        for (int i = 0; i < mCartArray.length(); i++) {
            ParseQuery<ParseObject> q = ParseQuery.getQuery("Product");
            q.getInBackground(mCartArray.getString(i), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    mProductList.add(object);
                    addProductViews(object);
                }
            });

        }
    }

    public void addProductViews(final ParseObject product) {
        final LinearLayout holder = (LinearLayout) findViewById(R.id.holder);

        final LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(params);
        ll.setPadding(getPixel(16), getPixel(16), getPixel(16), getPixel(16));
        holder.addView(ll);

        final ImageView im = new ImageView(this);

        final ParseFile image = product.getParseFile("image");
        image.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                mCount++;
                mPrice = mPrice + product.getNumber("cost").intValue();

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                im.setImageBitmap(bmp);
                im.setLayoutParams(new LinearLayout.LayoutParams(getPixel(100), getPixel(100)));
                ll.addView(im);


                TextView tv1 = new TextView(CheckoutActivity.this);
                tv1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                tv1.setGravity(Gravity.CENTER_VERTICAL);
                tv1.setText(product.getString("name"));
                tv1.setTextAppearance(getApplicationContext(), R.style.Body1);
                tv1.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                tv1.setPadding(getPixel(16), getPixel(0), getPixel(16), getPixel(0));
                ll.addView(tv1);

                TextView tv2 = new TextView(CheckoutActivity.this);
                tv2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                tv2.setGravity(Gravity.CENTER);
                tv2.setText("$ " + product.getNumber("cost").toString());
                tv1.setTextAppearance(getApplicationContext(), R.style.Body1);
                tv2.setTextColor(getResources().getColor(R.color.colorTextPrimary));
                ll.addView(tv2);

                if (mCount == mCartSize) {
                    TextView priceView = (TextView) findViewById(R.id.price);
                    priceView.setText("Total: $ " + mPrice);
                }
            }
        });

    }

    public int getPixel(int i) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics());
        return ((int) px);
    }

    public void onCheckoutClicked(View v) {
        try {
            deliveryEstimate();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void deliveryEstimate() throws JSONException, ParseException {
        for (int i = 0; i < mProductList.size(); i++) {
            ParseObject product = mProductList.get(i);
            ParseObject charity = product.getParseObject("parentOrg");
            charity.fetchIfNeeded();

            final float weight = product.getNumber("weight").floatValue();

            ParseQuery<ParseObject> q = ParseQuery.getQuery("Trips");
            q.whereGreaterThanOrEqualTo("freeBaggageSpace", weight);
            q.whereEqualTo("fromCity", charity.getString("location"));
            q.whereEqualTo("toCity", ParseUser.getCurrentUser().getString("location"));
            q.orderByDescending("arrivalDate");
            q.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject trip, ParseException e) {
                    if (trip != null) {
                        Number newFreeBaggageSpace = trip.getNumber("freeBaggageSpace").floatValue() - weight;
                        trip.put("freeBaggageSpace", newFreeBaggageSpace);
                        //TODO do whatever else
                    }
                    if (e == null)
                        Log.d(TravelCraftApp.TAG, "Delivery: " + trip.getDate("arrivalDate"));
                    else
                        Log.d(TravelCraftApp.TAG, "Exception: " + e);
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.support.v7.appcompat.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                logout();
            default:
                super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home: {
                startActivity(new Intent(this, HomeActivity.class));
                break;
            }
            case R.id.nav_profile:
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_cart:
                break;
            case R.id.nav_logout: {
                logout();
                break;
            }
            default:
                break;
        }

        menuItem.setChecked(true);
        mDrawer.closeDrawers();
    }

    private void logout() {
        // Log the user out
        ParseUser.logOut();
        // Go to the login view
        startLoginActivity();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, DispatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
