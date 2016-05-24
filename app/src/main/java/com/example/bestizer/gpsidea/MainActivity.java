package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends AppCompatActivity {

    Button setPos;
    EditText Latitude, Longitude;
    int Lat,Long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPos = (Button)this.findViewById(R.id.button);
        Latitude = (EditText)this.findViewById(R.id.latit);
        Longitude = (EditText)this.findViewById(R.id.longit);

        setPos.setOnClickListener(new Button.OnClickListener(){
            public void onClick (View v){
                Lat = Integer.parseInt(Latitude.getText().toString());
                Long = Integer.parseInt(Longitude.getText().toString());
                LatLng Pos = new LatLng(Lat,Long);
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("Latit",Lat);
                i.putExtra("Longit",Long);
                startActivity(i);

            }
        });

        System.out.println(""+Lat);
    }
}
