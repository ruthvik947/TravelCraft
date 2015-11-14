package com.rn.travelcraft.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.rn.travelcraft.R;
import com.rn.travelcraft.application.TravelCraftApp;

import java.util.Arrays;
import java.util.List;

public class SigninActivity extends AppCompatActivity {

    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Check if there is a currently logged in user
        // and it's linked to a Facebook account.
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity
            loginDone();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void onFbLoginClick(View v) {
        mProgressDialog = ProgressDialog.show(SigninActivity.this, "", "Logging in...", true);

        List<String> permissions = Arrays.asList("public_profile", "email");
        // NOTE: for extended permissions, like "user_about_me", your app must be reviewed by the Facebook team
        // (https://developers.facebook.com/docs/facebook-login/permissions/)

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                mProgressDialog.dismiss();
                if (user == null) {
                    Log.d(TravelCraftApp.TAG, "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d(TravelCraftApp.TAG, "User signed up and logged in through Facebook!");
                    loginDone();
                } else {
                    Log.d(TravelCraftApp.TAG, "User logged in through Facebook!");
                    loginDone();
                }
            }
        });
    }

    private void loginDone() {
        Intent intent = new Intent(this, DispatchActivity.class);
        startActivity(intent);
    }
}
