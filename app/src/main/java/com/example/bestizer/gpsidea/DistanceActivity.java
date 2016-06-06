package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import model.NamedLocation;

public class DistanceActivity extends AppCompatActivity {

    private TextView message;
    private EditText distanceField;
    private Button goodnight;
    private NamedLocation namedLocation;
    private model.AlarmLocation alarmLocation;
    private int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        message = (TextView) this.findViewById(R.id.textView);
        distanceField = (EditText) this.findViewById(R.id.editText);
        goodnight = (Button) this.findViewById(R.id.button);
        Intent i = getIntent();
        namedLocation = i.getParcelableExtra("model.NamedLocation");
    }


    public void onClick(View v) {
        distance = Integer.parseInt(distanceField.getText().toString());
        alarmLocation = new model.AlarmLocation(namedLocation, distance);
        Intent intent = new Intent(DistanceActivity.this, AlarmLocation.class);
        intent.putExtra("model.AlarmLocation", alarmLocation);
        startActivity(intent);

    }

}
