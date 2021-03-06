package model;

import android.os.Parcel;

/**
 * @author Hendrik Werner
 * @author Jasper Haasdijk
 * @author Janne van Den Hout
 * @author Arne Diehl
 */
public class AlarmLocation extends NamedLocation {

    public static final Creator<AlarmLocation> CREATOR = new Creator<AlarmLocation>() {
        @Override
        public AlarmLocation createFromParcel(Parcel in) {
            return new AlarmLocation(in);
        }

        @Override
        public AlarmLocation[] newArray(int size) {
            return new AlarmLocation[size];
        }
    };

    public final int radius;

    public AlarmLocation(NamedLocation nl, int radius) {
        super(nl);
        this.radius = radius;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(radius);
    }

    public int describeContents() {
        return 0;
    }

    protected AlarmLocation(Parcel in) {
        super(in);
        radius = in.readInt();
    }

}
