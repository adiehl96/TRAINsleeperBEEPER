package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import model.AlarmLocation;
import model.NamedLocation;

public class DistanceActivity extends AppCompatActivity{

    private TextView message;
    private EditText distanceField;
    private Button goodnight;

    private NamedLocation namedLocation;
    private AlarmLocation alarmLocation;

    private double distance;

    /*
    private double latitude;
    private double longitude;
     */
/*

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        message = (TextView)findViewById(R.id.textView);
        distanceField = (EditText)findViewById(R.id.editText);
        goodnight = (Button)findViewById(R.id.button);

        Intent i = getIntent();
        namedLocation = (NamedLocation) i.getParcelableExtra("namedLocation");

        /*
        Intent intent = getIntent();
        latitude = intent.getIntExtra("Latit", -1);
        longitude = intent.getIntExtra("Longit", -1);
        */
        /*
    }


        public void onClick(){

            distance = Integer.parseInt(distanceField.getText().toString());
            /*
            Intent intent = new Intent(this,alarm.class);
            intent.putExtra("Latitude", latitude);
            intent.putExtra("Longitude", longitude);
            intent.putExtra("Distance", distance);
            startActivity(intent);

            Intent intent = new Intent(this,alarm.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("alarm", alarmLocation);
            intent.putExtras(bundle);
            startActivity(intent);

    }*/

}
