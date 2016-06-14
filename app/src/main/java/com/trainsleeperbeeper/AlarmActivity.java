package com.trainsleeperbeeper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import model.AlarmLocation;

/**
 * @author Hendrik Werner
 * @author Jasper Haasdijk
 * @author Janne van Den Hout
 * @author Arne Diehl
 */
public class AlarmActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int PERMISSIONS_REQUEST_LOCATION_ID = 98;
    private static final long[] VIBRATION_PATTERN = {0, 1000, 1000};

    private GoogleApiClient googleApiClient;
    private TextView distance;
    private Location currentLocation;
    private LocationRequest locationRequest;
    private AlarmLocation destination;
    private Ringtone ringtone;
    private Vibrator vibrator;
    private Switch switchVibration;
    private Switch switchRingtone;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        updateAndCheck();
    }

    public void cancelAlarm(View v) {
        deactivateVibration();
        deactivateRingtone();
        Intent i = new Intent(AlarmActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        destination = getIntent().getParcelableExtra("model.AlarmLocation");
        distance = (TextView) this.findViewById(R.id.distanceText);
        switchVibration = (Switch) this.findViewById(R.id.switchVibration);
        switchRingtone = (Switch) this.findViewById(R.id.switchRingtone);
        createGoogleApiClient();
        createLocationRequest();
        new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        ringtone = RingtoneManager.getRingtone(
                getApplicationContext(),
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        );
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Toast.makeText(this, "Alarm is active now.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
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

    private void updateAndCheck() {
        float dist = currentLocation.distanceTo(destination);
        distance.setText(String.format("Remaining Distance\n%.1f km", dist / 1000));
        if (dist < destination.radius) {
            if (switchVibration.isChecked()) {
                vibrator.vibrate(VIBRATION_PATTERN, 0);
            }
            if (switchRingtone.isChecked()) {
                ringtone.play();
            }
        }
    }

    private void createGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void deactivateRingtone() {
        if (ringtone != null) {
            ringtone.stop();
        }
    }

    private void deactivateVibration() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

}
