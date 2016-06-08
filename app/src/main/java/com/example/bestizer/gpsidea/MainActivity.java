package com.example.bestizer.gpsidea;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int PERMISSIONS_REQUEST_LOCATION_ID = 97;
    private Toast noInternetToast, noLocationPermissionToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noInternetToast = Toast.makeText(this, "No Internet Connection available.", Toast.LENGTH_SHORT);
        noLocationPermissionToast = Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT);
    }

    private boolean networkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        } else {
            noLocationPermissionToast.show();
        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION_ID
            );
            return false;
        }
    }

    public void handleMapButton(View v) {
        if (checkPermission()) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }
    }

    public void handleTrainButton(View v) {
        if (networkAvailable()) {
            startActivity(new Intent(MainActivity.this, StationChoiceActivity.class));
        } else {
            noInternetToast.show();
        }
    }

}
