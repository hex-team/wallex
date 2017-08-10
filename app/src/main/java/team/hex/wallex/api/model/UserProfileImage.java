package team.hex.wallex.api.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfileImage implements Parcelable {
    private String small;
    private String large;
    private String medium;

    protected UserProfileImage(Parcel in) {
        small = in.readString();
        large = in.readString();
        medium = in.readString();
    }

    public static final Creator<UserProfileImage> CREATOR = new Creator<UserProfileImage>() {
        @Override
        public UserProfileImage createFromParcel(Parcel in) {
            return new UserProfileImage(in);
        }

        @Override
        public UserProfileImage[] newArray(int size) {
            return new UserProfileImage[size];
        }
    };

    public String getSmall() {
        return this.small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return this.large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return this.medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(small);
        parcel.writeString(large);
        parcel.writeString(medium);
    }
}
