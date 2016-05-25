package model;

/**
 * @author Hendrik Werner
 */
public class GpsLocation {
    public final String name;
    public final double latitude;
    public final double longitude;

    public GpsLocation(String name, double lat, double lon) {
        this.name = name;
        latitude = lat;
        longitude = lon;
    }
}