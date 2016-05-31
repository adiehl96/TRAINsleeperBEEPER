package model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author Hendrik Werner
 */
public class NamedLocation extends Location implements Parcelable {

    public final String name;
    public Location loco;
    public NamedLocation(String name) {
        super("");
        this.name = name;
    }


    public NamedLocation(Location location, String name){
        super(location);
        this.name=name;
    }



/*
    protected NamedLocation(Parcel in) {
        super("");
        Location l = Location.CREATOR.createFromParcel(in);
        NamedLocation lp = new NamedLocation(l,"");
        name = in.readString();
        return lp;
    }
*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NamedLocation> CREATOR = new Parcelable.Creator<NamedLocation>() {
        @Override
        public NamedLocation createFromParcel(Parcel in) {
            Location l = Location.CREATOR.createFromParcel(in);
            NamedLocation lp = new NamedLocation(l,in.readString());

            return lp;
        }

        @Override
        public NamedLocation[] newArray(int size) {
            return new NamedLocation[size];
        }
    };
}