package com.rn.travelcraft.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.parse.ParseUser;
import com.rn.travelcraft.R;
import com.rn.travelcraft.parseData.Trip;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;
import java.util.Date;

public class RegisterAsCourierActivity extends AppCompatActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener {

    private static final String TAG = RegisterAsCourierActivity.class.getSimpleName();
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private final String[] fromCities = new String[] {
        "Kampala", "Guatemala", "Baku", "Bangalore", "Nairobi", "Jharkhand"
    };

    private final String[] toCities = new String[] {
        "Los Angeles", "Bangalore", "New York", "San Francisco", "London", "Barcelona", "Seattle"
    };

    private final String[] freeSpaceOptions = new String[] {
            "0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "5.0", "7.5", "10.0"
    };

    private TextView fromCity;
    private TextView toCity;
    private TextView spaceText;
    private TextView mDepartureDateText;
    private TextView mArrivalDateText;

    private MaterialBetterSpinner fromSpinner;
    private MaterialBetterSpinner toSpinner;
    private MaterialBetterSpinner spaceSpinner;

    private Trip mTrip;

    private boolean mIsDepartureDateSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_courier);

        initializeViews();
        addListeners();

    }

    private void initializeViews() {
        mTrip = new Trip();
        mTrip.setTraveller(ParseUser.getCurrentUser());

        fromCity = (TextView) findViewById(R.id.from_city_text);
        toCity = (TextView) findViewById(R.id.to_city_text);
        spaceText = (TextView) findViewById(R.id.free_space_text);

        ArrayAdapter<String> fromCitiesAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, fromCities);

        fromSpinner = (MaterialBetterSpinner) findViewById(R.id.from_cities_spinner);
        fromSpinner.setAdapter(fromCitiesAdapter);

        ArrayAdapter<String> toCitiesAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, toCities);

        toSpinner = (MaterialBetterSpinner) findViewById(R.id.to_cities_spinner);
        toSpinner.setAdapter(toCitiesAdapter);

        ArrayAdapter<String> spaceAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, freeSpaceOptions);

        spaceSpinner = (MaterialBetterSpinner) findViewById(R.id.space_spinner);
        spaceSpinner.setAdapter(spaceAdapter);

        Calendar now = Calendar.getInstance();

        mDepartureDateText = (TextView) findViewById(R.id.departureDate);
        mDepartureDateText.setText(now.get(Calendar.MONTH) + "/" + now.get(Calendar.DAY_OF_MONTH) + "/" + now.get(Calendar.YEAR));
        mArrivalDateText = (TextView) findViewById(R.id.arrivalDate);
        mArrivalDateText.setText(now.get(Calendar.MONTH) + "/" + now.get(Calendar.DAY_OF_MONTH) + "/" + now.get(Calendar.YEAR));
    }

    private void addListeners() {
        fromSpinner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = (String) parent.getAdapter().getItem(position);
                mTrip.setFromCity(city);
                fromCity.setText(city);
                toSpinner.requestFocus();
            }
        });

        toSpinner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = (String) parent.getAdapter().getItem(position);
                mTrip.setToCity(city);
                toCity.setText(city);
                spaceSpinner.requestFocus();
            }
        });

        spaceSpinner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String space = (String) parent.getAdapter().getItem(position);
                mTrip.setFreeBaggageSpace(space);
                spaceText.setText(space);
            }
        });
    }

    public void onDateClicked(View view) {

        if (view.getId() == R.id.departureDate)
            mIsDepartureDateSet = true;

        FragmentManager fm = getSupportFragmentManager();
        Calendar now = Calendar.getInstance();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = CalendarDatePickerDialogFragment
                .newInstance(RegisterAsCourierActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
        calendarDatePickerDialogFragment.show(fm, FRAG_TAG_DATE_PICKER);
    }

    public void onNextClicked(View view) {
        // Get all information and store as new ParseObject
//        Slide slide = new Slide();
//        slide.setDuration(1000);
//        getWindow().setExitTransition(slide);
        mTrip.parseUpload();
        Intent intent = new Intent(this, ConfirmTravelActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

        if (mIsDepartureDateSet) {
            mIsDepartureDateSet = false;
            mDepartureDateText.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
            Date date = new Date();
            date.setDate(dayOfMonth);
            date.setMonth(monthOfYear);
            date.setYear(year - 1900);
            mTrip.setDepartureDate(date);
            //Log.d(TravelCraftApp.TAG, "Dep: "+mTrip.getDepartureDate() + "year: " + year + "date year: "+date.getYear());
        } else {
            mArrivalDateText.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
            Date date = new Date();
            date.setDate(dayOfMonth);
            date.setMonth(monthOfYear);
            date.setYear(year - 1900);
            mTrip.setArrivalDate(date);
            //Log.d(TravelCraftApp.TAG, "Arr: " + mTrip.getArrivalDate());
        }
    }
}
