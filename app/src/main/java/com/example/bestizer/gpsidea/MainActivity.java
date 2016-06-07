package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleMapButton(View v) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    }

    public void handleTrainButton(View v) {
        startActivity(new Intent(MainActivity.this, StationChoiceActivity.class));
    }

}
