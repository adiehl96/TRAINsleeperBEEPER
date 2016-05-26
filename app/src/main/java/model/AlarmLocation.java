package model;


/**
 * Created by Bestizer on 26-5-2016.
 */
public class AlarmLocation extends NamedLocation{

    public double radius;

    public AlarmLocation(NamedLocation NamedLocation, double radius) {
        super(NamedLocation,NamedLocation.name);
        this.radius=radius;
    }
}
