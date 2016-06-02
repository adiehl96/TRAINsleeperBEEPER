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

    private TextView message;
    EditText distanceField;
    private Button goodnight;

    NamedLocation namedLocation;
    private AlarmLocation alarmLocation;

    double distance;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        message = (TextView) this.findViewById(R.id.textView);
        distanceField = (EditText)this.findViewById(R.id.editText);
        goodnight = (Button)this.findViewById(R.id.button);

        Intent i = getIntent();
        namedLocation =  i.getParcelableExtra("model.NamedLocation");



    }


        public void onClick(View v){
            distance = Double.parseDouble(distanceField.getText().toString());
            Intent intent = new Intent(DistanceActivity.this,alarm.class);
            intent.putExtra("model.NamedLocation", namedLocation);
            intent.putExtra("Distance", distance);
            startActivity(intent);

    }

}
