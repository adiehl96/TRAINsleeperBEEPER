package com.example.bestizer.gpsidea;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import model.NamedLocation;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

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
import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    Location mLastLocation;
    BreakIterator mLatitudeText;
    BreakIterator mLongitudeText;
    Button getPos;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //put buttons after setcontentview
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        getPos = (Button)this.findViewById(R.id.button2);
        getPos.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this,DistanceActivity.class);
                NamedLocation nl = new NamedLocation("Map location");
                nl.setLatitude(latitude);
                nl.setLongitude(longitude);
                i.putExtra("model.NamedLocation",nl);

                startActivity(i);
            }
        });





    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public void checkpermission(GoogleMap mzp) {

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            mzp.setMyLocationEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent = getIntent();
        int lat = intent.getIntExtra("Latit",-1);
        int Longit = intent.getIntExtra("Longit",-1);

        mMap = googleMap;

        checkpermission(mMap);

        // Add a marker in Sydney and move the camera
        LatLng custom = new LatLng(lat,Longit);

        mMap.addMarker(new MarkerOptions().position(custom).title("Marker in custom city"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(custom));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point));
                latitude = point.latitude;
                longitude= point.longitude;
            }
        });

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
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



}
