package com.rn.travelcraft.application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class TravelCraftApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Parse initialization/permissions
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Qt1kfx56DpzLgywLINf43f0nZAycmqVdSnOUBRfl", "pW9Z1oiQ7lBRYFweKfOECRtJJQmw3KcAMeUkJqVK");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("cmon", "yay");
        testObject.saveInBackground();
    }
}
