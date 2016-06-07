package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import android.Manifest;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;

public class AlarmActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final int PERMISSIONS_REQUEST_LOCATION_ID = 98;
    public GoogleApiClient googleApiClient;
    private TextView distance;
    private TextView message;
    private Location currentLocation;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private model.AlarmLocation destination;
    private boolean vibrateEnable = true;
    private boolean ringtoneEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        Intent intent = getIntent();
        destination = intent.getParcelableExtra("model.AlarmLocation");
        currentLocation = new Location("current");
        distance = (TextView) this.findViewById(R.id.textView);
        message = (TextView) this.findViewById(R.id.textView2);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        locationRequest = new LocationRequest();
        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkpermission();
        if (lastLocation != null) {
            currentLocation.setLatitude(lastLocation.getLatitude());
            currentLocation.setLongitude(lastLocation.getLongitude());
        }
        distance.setText((int) (currentLocation.distanceTo(destination) / 1000) + " km");
        if (currentLocation.distanceTo(destination) < destination.radius) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            message.setText("You're there");
        } else {
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

    public void checkpermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION_ID);

        }
    }

    protected void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(800);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION_ID
            );
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        updateAndCheck();
    }

    private void updateAndCheck() {
        distance.setText((int) (currentLocation.distanceTo(destination) / 1000) + " km");
        if (currentLocation.distanceTo(destination) < destination.radius) {
            message.setText("You're there");
            if (vibrateEnable) {
                //vibrate();
            } else if (ringtoneEnable) {
                //playAlarm();
            }
        } else {
            message.setText("Not yet there");
        }
    }

}
