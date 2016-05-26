package model;

import android.location.Location;

/**
 * @author Hendrik Werner
 */
public class NamedLocation extends Location {

    public final String name;

    public NamedLocation(String name) {
        super("");
        this.name = name;
    }
    public NamedLocation(Location location, String name){
        super(location);
        this.name=name;
    }



}