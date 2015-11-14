package com.rn.travelcraft.application;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;


public class TravelCraftApp extends Application {

    public static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        // Parse initialization/permissions
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Qt1kfx56DpzLgywLINf43f0nZAycmqVdSnOUBRfl", "pW9Z1oiQ7lBRYFweKfOECRtJJQmw3KcAMeUkJqVK");

        //Initialize Facebook SDk
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Initialize Parse Facebook integration
        ParseFacebookUtils.initialize(getApplicationContext());


    }
}
