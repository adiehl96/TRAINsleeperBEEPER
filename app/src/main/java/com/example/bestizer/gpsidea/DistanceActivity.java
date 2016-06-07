package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import model.NamedLocation;

public class DistanceActivity extends AppCompatActivity {

    private Toast invalidDistanceToast;
    private EditText distanceField;
    private NamedLocation namedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        distanceField = (EditText) this.findViewById(R.id.editText);
        namedLocation = getIntent().getParcelableExtra("model.NamedLocation");
        invalidDistanceToast = Toast.makeText(this, "You must enter a valid distance.", Toast.LENGTH_SHORT);
    }

    public void onClick(View v) {
        try {
            int distance = Integer.parseInt(distanceField.getText().toString()) * 1000;
            Intent intent = new Intent(DistanceActivity.this, AlarmActivity.class);
            intent.putExtra("model.AlarmLocation", new model.AlarmLocation(namedLocation, distance));
            startActivity(intent);
        } catch (NumberFormatException ex) {
            invalidDistanceToast.show();
        }
    }

}
