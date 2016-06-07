package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import model.AlarmLocation;
import model.NamedLocation;

public class DistanceActivity extends AppCompatActivity{

    EditText distanceField;
    NamedLocation namedLocation;
    private AlarmLocation alarmLocation;
    int distance;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        distanceField = (EditText)this.findViewById(R.id.editText);

        Intent i = getIntent();
        namedLocation =  i.getParcelableExtra("model.NamedLocation");

    }


        public void onClick(View v){
            distance = Integer.parseInt(distanceField.getText().toString());
            alarmLocation = new AlarmLocation(namedLocation,distance);
            Intent intent = new Intent(DistanceActivity.this,alarm.class);
            intent.putExtra("model.AlarmLocation", alarmLocation);
            startActivity(intent);

    }

}
