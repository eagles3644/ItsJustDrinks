package com.itsjustdrinks.itsjustdrinks.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itsjustdrinks.itsjustdrinks.R;
import com.itsjustdrinks.itsjustdrinks.utils.Constants;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private int mPermRequestCode = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onResume(){

        if(hasRequiredPermissions() && hasAuthenticated()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            delayedStartNextActivity(intent);
        } else {
            if(!hasRequiredPermissions()){
                requestMissingPermissions();
            }
            if(!hasAuthenticated()){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                delayedStartNextActivity(intent);
            }
        }

        super.onResume();
    }

    private boolean hasRequiredPermissions(){
        return hasAccessNetworkStatePerm() && hasAccessWifiStatePerm() && hasInternetPerm()
                && hasReceiveBootCompletedPerm() && hasAccessFineLocationPerm()
                && hasReadPhoneStatePerm();
    }

    private boolean hasAccessNetworkStatePerm(){
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasAccessWifiStatePerm(){
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasInternetPerm(){
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasReceiveBootCompletedPerm(){
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECEIVE_BOOT_COMPLETED) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasAccessFineLocationPerm(){
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasReadPhoneStatePerm(){
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestMissingPermissions(){
        String[] permString = new String[6];
        permString[0] = Manifest.permission.ACCESS_NETWORK_STATE;
        permString[1] = Manifest.permission.ACCESS_WIFI_STATE;
        permString[2] = Manifest.permission.INTERNET;
        permString[3] = Manifest.permission.ACCESS_FINE_LOCATION;
        permString[4] = Manifest.permission.RECEIVE_BOOT_COMPLETED;
        permString[5] = Manifest.permission.READ_PHONE_STATE;
        ActivityCompat.requestPermissions(this, permString, mPermRequestCode);
    }

    private boolean hasAuthenticated(){
        return mPrefs.getBoolean(Constants.PREF_USER_AUTHENTICATED, false);
    }

    private void delayedStartNextActivity(final Intent intent) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, Constants.SPLASH_DELAY_MILIS);
    }

    private void delayedFinish() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, Constants.SPLASH_DELAY_MILIS);
    }
}
