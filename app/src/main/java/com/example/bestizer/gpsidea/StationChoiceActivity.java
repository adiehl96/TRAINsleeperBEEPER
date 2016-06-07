package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.NamedLocation;
import model.NamedLocationProvider;
import model.NsApiParser;

public class StationChoiceActivity extends AppCompatActivity {

    private NamedLocationProvider parser;
    private List<NamedLocation> listNamedLocation;
    private AutoCompleteTextView stationChoice;
    private List<String> listStationNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_choice);
        stationChoice = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        try {
            parser = new NsApiParser();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listNamedLocation = (List<NamedLocation>) parser.getLocations();
        listStationNames = new ArrayList<>();
        getStationNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listStationNames);
        stationChoice.setAdapter(adapter);

    }

    private void getStationNames() {
        for (NamedLocation location : listNamedLocation) {
            listStationNames.add(location.name);
        }
    }

    private NamedLocation findLocation(String stationName) {
        for (NamedLocation location : listNamedLocation) {
            if (location.name.equals(stationName)) {
                return location;
            }
        }
        return null;
    }

    public void onClick(View v) {
        Intent intent = new Intent(StationChoiceActivity.this, DistanceActivity.class);
        intent.putExtra("model.NamedLocation", findLocation(stationChoice.getText().toString()));
        startActivity(intent);
    }


}
