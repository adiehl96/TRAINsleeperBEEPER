package model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Hendrik Werner
 * @author Jasper Haasdijk
 * @author Janne van Den Hout
 * @author Arne Diehl
 */
public class NamedLocation extends Location {

    public static final Parcelable.Creator<NamedLocation> CREATOR = new Parcelable.Creator<NamedLocation>() {
        @Override
        public NamedLocation createFromParcel(Parcel in) {
            return new NamedLocation(in);
        }

        @Override
        public NamedLocation[] newArray(int size) {
            return new NamedLocation[size];
        }
    };

    public final String name;

    public NamedLocation(String name) {
        super("");
        this.name = name;
    }

    public NamedLocation(NamedLocation nl) {
        super(nl);
        this.name = nl.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
    }

    protected NamedLocation(Parcel in) {
        super(Location.CREATOR.createFromParcel(in));
        name = in.readString();
    }

}