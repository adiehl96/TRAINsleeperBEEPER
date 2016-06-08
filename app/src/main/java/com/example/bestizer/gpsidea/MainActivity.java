package com.example.bestizer.gpsidea;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    public void handleMapButton(View v) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    }

    public void handleTrainButton(View v) {
        if(isNetworkAvailable()) {
            startActivity(new Intent(MainActivity.this, StationChoiceActivity.class));
        }
        else{
            Toast toast = Toast.makeText(this,"No Internet Connection available", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
