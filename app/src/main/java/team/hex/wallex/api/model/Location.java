package team.hex.wallex.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Location.
 * */

public class Location implements Parcelable {

    /**
     * city : Montreal
     * country : Canada
     * position : {"latitude":45.4732984,"longitude":-73.6384879}
     */
    private String city;
    private String country;

    private Position position;

    /** <br> parcel. */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeParcelable(this.position, flags);
    }

    public Location() {
    }

    protected Location(Parcel in) {
        this.city = in.readString();
        this.country = in.readString();
        this.position = in.readParcelable(Position.class.getClassLoader());
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
