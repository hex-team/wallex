package team.hex.wallpaper.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Photo links.
 * */

public class PhotoLinks implements Parcelable {

    /**
     * self : https://api.unsplash.com/photos/Dwu85P9SOIk
     * html : https://unsplash.com/photos/Dwu85P9SOIk
     * download : https://unsplash.com/photos/Dwu85P9SOIk/download
     * download_location : https://api.unsplash.com/photos/Dwu85P9SOIk/download
     */
    private String self;
    private String html;
    private String download;
    private String download_location;

    /** <br> parcel. */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.self);
        dest.writeString(this.html);
        dest.writeString(this.download);
        dest.writeString(this.download_location);
    }

    public PhotoLinks() {
    }

    protected PhotoLinks(Parcel in) {
        this.self = in.readString();
        this.html = in.readString();
        this.download = in.readString();
        this.download_location = in.readString();
    }

    public static final Creator<PhotoLinks> CREATOR = new Creator<PhotoLinks>() {
        @Override
        public PhotoLinks createFromParcel(Parcel source) {
            return new PhotoLinks(source);
        }

        @Override
        public PhotoLinks[] newArray(int size) {
            return new PhotoLinks[size];
        }
    };

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownload_location() {
        return download_location;
    }

    public void setDownload_location(String download_location) {
        this.download_location = download_location;
    }
}
