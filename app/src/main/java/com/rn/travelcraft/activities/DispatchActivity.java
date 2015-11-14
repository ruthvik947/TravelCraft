package com.rn.travelcraft.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseUser;
import com.rn.travelcraft.R;
import com.rn.travelcraft.application.TravelCraftApp;

import org.json.JSONException;
import org.json.JSONObject;

public class DispatchActivity extends AppCompatActivity {

    public static final String TAG = DispatchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);

        Log.d(TAG, "DispatchActivity onCreate");

        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            ParseUser currentUser = ParseUser.getCurrentUser();
            if ((currentUser != null) && currentUser.isAuthenticated()) {
                storeData();
            }
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, SigninActivity.class));
        }
    }

    public void storeData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        if (jsonObject != null) {
                            ParseUser currentUser = ParseUser.getCurrentUser();

                            try {
                                currentUser.put("name", jsonObject.getString("name"));

                                if (jsonObject.getString("email") != null) {
                                    currentUser.put("email", jsonObject.get("email"));
                                    currentUser.put("username", jsonObject.get("email"));
                                }

                                currentUser.saveInBackground();

                            } catch (JSONException e) {
                                Log.d(TAG, "Error parsing returned user data. " + e);
                            }
                        } else if (graphResponse.getError() != null) {
                            switch (graphResponse.getError().getCategory()) {
                                case LOGIN_RECOVERABLE:
                                    Log.d(TAG, "Authentication error: " + graphResponse.getError());
                                    break;

                                case TRANSIENT:
                                    Log.d(TAG, "Transient error. Try again. " + graphResponse.getError());
                                    break;

                                case OTHER:
                                    Log.d(TAG, "Some other error: " + graphResponse.getError());
                                    break;
                            }
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
