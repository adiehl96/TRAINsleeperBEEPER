package model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author Hendrik Werner
 */
public class NamedLocation extends Location implements Parcelable{

    public final String name;

    public NamedLocation(String name) {
        super("");
        this.name = name;
    }

    public NamedLocation(Location location, String name){
        super(location);
        this.name=name;
    }

    /*protected NamedLocation(Parcel in) {
        super(in);
        name = in.readString();
    }

    public static final Creator<NamedLocation> CREATOR = new Creator<NamedLocation>() {
        @Override
        public NamedLocation createFromParcel(Parcel in) {
            return new NamedLocation(in);
        }

        @Override
        public NamedLocation[] newArray(int size) {
            return new NamedLocation[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
    }

    public int describeContents() {
        return 0;
    }
*/

}