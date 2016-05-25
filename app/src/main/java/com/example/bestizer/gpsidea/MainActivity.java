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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPos = (Button) this.findViewById(R.id.button);


        setPos.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("Latit", 52);
                i.putExtra("Longit", 5);
                startActivity(i);

            }
        });

    }

}
