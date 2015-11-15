package com.rn.travelcraft.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rn.travelcraft.R;

public class ConfirmTravelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_travel);

    }

    // Do nothing on back pressed
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    
}
