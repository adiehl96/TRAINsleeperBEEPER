package com.example.bestizer.gpsidea;

import model.NamedLocation;
import model.AlarmLocation;
import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
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


public class alarm extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener  {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 98;
    public GoogleApiClient mGoogleApiClient;
    TextView lattest,message,rad;
    double lat, lng;
    LatLng destination;
    Location mCurrentLocation;
    double distance;
    double radius;
    String source;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    NamedLocation test;
    AlarmLocation tset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        //test = new NamedLocation("aasdasd");
        //tset = new AlarmLocation(test,50.0);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("Latit", -1);
        lng = intent.getDoubleExtra("Longit", -1);
        destination = new LatLng(lat, lng);
        mCurrentLocation = new Location("current");


        //lattest = (TextView) this.findViewById(R.id.textView);
        //message = (TextView) this.findViewById(R.id.textView2);
        //rad     = (TextView) this.findViewById(R.id.textView3);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mLocationRequest = new LocationRequest();
        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        //PendingResult<LocationSettingsResult> result =
                //LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                  //      builder.build());


        rad.setText(""+source);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        checkpermission();
        if (mLastLocation != null) {
            mCurrentLocation.setLatitude(mLastLocation.getLatitude());
            mCurrentLocation.setLongitude(mLastLocation.getLongitude());
        }

        calcdist();

        lattest.setText(distance + "");
        if(distance < 60.0) {
            message.setText( "You're there");
        }
        else{
            message.setText("Not yet there");
            startLocationUpdates();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void calcdist() {


        double earthRadius = 3958.75;

        double dLat = Math.toRadians(destination.latitude - mCurrentLocation.getLatitude());

        double dLng = Math.toRadians(destination.longitude - mCurrentLocation.getLongitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(mCurrentLocation.getLatitude()))
                * Math.cos(Math.toRadians(destination.latitude)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = earthRadius * c;

        double meterConversion = 1609.0;

        distance = (dist * meterConversion);
    }

    public void checkpermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        }
    }


    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        }

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateAndCheck();

    }

    private void updateAndCheck(){
        calcdist();
        lattest.setText(distance + "");
        if(distance < 60.0) {
            message.setText( "You're there");

        }
        else{
            message.setText("Not yet there");

        }
    }
}
