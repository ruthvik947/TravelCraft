package com.rn.travelcraft.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rn.travelcraft.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class RegisterAsCourierActivity extends AppCompatActivity {

    private static final String TAG = RegisterAsCourierActivity.class.getSimpleName();

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

    private MaterialBetterSpinner fromSpinner;
    private MaterialBetterSpinner toSpinner;
    private MaterialBetterSpinner spaceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_courier);

        initializeViews();
        addListeners();

    }

    private void initializeViews() {
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
    }

    private void addListeners() {
        fromSpinner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = (String) parent.getAdapter().getItem(position);
                fromCity.setText(city);
                toSpinner.requestFocus();
            }
        });

        toSpinner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = (String) parent.getAdapter().getItem(position);
                toCity.setText(city);
                spaceSpinner.requestFocus();
            }
        });

        spaceSpinner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = (String) parent.getAdapter().getItem(position);
                spaceText.setText(content);
            }
        });
    }

    public void onNextClicked(View view) {
        // Get all information and store as new ParseObject
//        Slide slide = new Slide();
//        slide.setDuration(1000);
//        getWindow().setExitTransition(slide);

        Intent intent = new Intent(this, ConfirmTravelActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
