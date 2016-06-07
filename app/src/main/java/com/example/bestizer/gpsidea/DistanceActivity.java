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

    private EditText distanceField;
    private NamedLocation namedLocation;
    private int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        distanceField = (EditText) this.findViewById(R.id.editText);
        namedLocation = getIntent().getParcelableExtra("model.NamedLocation");
    }

    public void onClick(View v) {
        distance = Integer.parseInt(distanceField.getText().toString());
        Intent intent = new Intent(DistanceActivity.this, AlarmActivity.class);
        intent.putExtra("model.AlarmLocation", new model.AlarmLocation(namedLocation, distance));
        startActivity(intent);
    }

}