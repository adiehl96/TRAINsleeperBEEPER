package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton map;
    private ImageButton train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        train = (ImageButton) this.findViewById(R.id.imageButton);
        map = (ImageButton) this.findViewById(R.id.imageButton2);
    }

    public void handleMapButton(View v) {
        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(i);
    }

    public void handleTrainButton(View v) {
        Intent i = new Intent(MainActivity.this, StationChoiceActivity.class);
        startActivity(i);
    }

}
