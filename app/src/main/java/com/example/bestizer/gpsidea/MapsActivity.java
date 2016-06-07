package com.example.bestizer.gpsidea;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import model.NamedLocation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    private GoogleMap googleMap;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        createGoogleApiClient();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public void checkPermission(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION
            );
        }
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        double lat = 52.2333333;
        double lon = 5.66666667;
        googleMap = map;
        checkPermission(googleMap);
        LatLng custom = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(custom).title("Marker in custom city"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(custom));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(7.0f));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                map.clear();
                map.addMarker(new MarkerOptions().position(point));
                latitude = point.latitude;
                longitude = point.longitude;
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void handleChooseButton(View v) {
        NamedLocation nl = new NamedLocation("Map location");
        nl.setLatitude(latitude);
        nl.setLongitude(longitude);
        Intent i = new Intent(MapsActivity.this, DistanceActivity.class);
        i.putExtra("model.NamedLocation", nl);
        startActivity(i);
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

}
