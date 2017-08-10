package team.hex.wallex.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Exif.
 * */

public class Exif implements Parcelable {

    /**
     * make : Canon
     * model : Canon EOS 40D
     * exposure_time : 0.011111111111111112
     * aperture : 4.970854
     * focal_length : 37
     * iso : 100
     */
    private String make;
    private String model;
    private String exposure_time;
    private String aperture;
    private String focal_length;
    private int iso;

    /** <br> parcel. */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.make);
        dest.writeString(this.model);
        dest.writeString(this.exposure_time);
        dest.writeString(this.aperture);
        dest.writeString(this.focal_length);
        dest.writeInt(this.iso);
    }

    public Exif() {
    }

    protected Exif(Parcel in) {
        this.make = in.readString();
        this.model = in.readString();
        this.exposure_time = in.readString();
        this.aperture = in.readString();
        this.focal_length = in.readString();
        this.iso = in.readInt();
    }

    public static final Creator<Exif> CREATOR = new Creator<Exif>() {
        @Override
        public Exif createFromParcel(Parcel source) {
            return new Exif(source);
        }

        @Override
        public Exif[] newArray(int size) {
            return new Exif[size];
        }
    };

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getExposure_time() {
        return exposure_time;
    }

    public void setExposure_time(String exposure_time) {
        this.exposure_time = exposure_time;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getFocal_length() {
        return focal_length;
    }

    public void setFocal_length(String focal_length) {
        this.focal_length = focal_length;
    }

    public int getIso() {
        return iso;
    }

    public void setIso(int iso) {
        this.iso = iso;
    }
}
