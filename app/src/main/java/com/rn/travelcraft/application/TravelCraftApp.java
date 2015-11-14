package com.rn.travelcraft.application;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;


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

        //Parse Twitter integration
        ParseTwitterUtils.initialize("vYwu1T9oQQ1u83l5nsgUmYLBq", "ftmf1Pf6bpy8F3FaG8v7fsPbc9uo4w5QhT2Ubtr77qXvMEy8sv");

    }
}
