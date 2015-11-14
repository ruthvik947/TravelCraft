package com.rn.travelcraft.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import com.rn.travelcraft.R;

public class RegisterAsCourierActivity extends AppCompatActivity {

    private final String[] fromCities = new String[] {
        "Kampala", "Guatemala", "Baku", "Bangalore", "Nairobi"
    };

    private final String[] toCities = new String[] {
        "Los Angeles", "Bangalore", "New York", "San Francisco", "London", "Barcelona", "Seattle"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_courier);

        ArrayAdapter<String> fromCitiesAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, fromCities);

    }

}
