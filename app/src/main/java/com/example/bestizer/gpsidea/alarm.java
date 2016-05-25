package com.example.bestizer.gpsidea;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.BreakIterator;


public class alarm extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 98;
    public GoogleApiClient mGoogleApiClient;

    TextView lattest;
    double lat,lng;
    LatLng destination;
    LatLng start;
    double distance;
    Location mLastLocation;
    BreakIterator mLatitudeText;
    BreakIterator mLongitudeText;


    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("Latit",-1);
        lng = intent.getDoubleExtra("Longit",-1);
        destination = new LatLng(lat,lng);

        lattest = (TextView)this.findViewById(R.id.textView);
        lattest.setText(lat+"");

        if (mGoogleApiClient == null){
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                        .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                        .addApi(LocationServices.API)
                        .build();
            }

        calcdist();

    }

    @Override
    protected void onStart() {
        //mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        checkpermission(mLastLocation);
        if (mLastLocation != null) {

            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void calcdist(){


            double earthRadius = 3958.75;

            double dLat = Math.toRadians(destination.latitude - start.latitude);

            double dLng = Math.toRadians(destination.longitude - start.longitude);

            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(start.latitude))
                    * Math.cos(Math.toRadians(destination.latitude)) * Math.sin(dLng / 2)
                    * Math.sin(dLng / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double dist = earthRadius * c;

            double meterConversion = 1609.0;

        distance = (dist * meterConversion);
    }

    public void checkpermission(Location loco) {

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            loco = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        }
    }
}
