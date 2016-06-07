package com.example.bestizer.gpsidea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.app.AppCompatActivity;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.NamedLocation;
import model.NamedLocationProvider;
import model.NsApiParser;

public class StationChoiceActivity extends AppCompatActivity{

    NamedLocationProvider parser;
    List<NamedLocation> listNamedLocation;
    AutoCompleteTextView stationChoice;
    List<String> listStationNames;
    String stationname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_choice);
        System.out.println("yesyesyes");
        stationChoice = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listStationNames);
        stationChoice.setAdapter(adapter);

    }

    private void getStationNames(){
        for(NamedLocation location: listNamedLocation){
            listStationNames.add(location.name);
        }
    }

    private NamedLocation findLocation(String stationname){
        for(NamedLocation location : listNamedLocation){
            if(location.name.equals(stationname)){
                return location;
            }
        }
        return null;
    }

    public void onClick(View v){
        stationname = stationChoice.getText().toString();
        Intent intent = new Intent(StationChoiceActivity.this,DistanceActivity.class );
        intent.putExtra("model.NamedLocation",findLocation(stationname));
        startActivity(intent);
    }



}
