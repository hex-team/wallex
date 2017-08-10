package team.hex.wallex.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Tag.
 * */

public class Tag implements Parcelable {

    /**
     * title : frozen
     * url : https://images.unsplash.com/photo-1420466721261-818d807296a1
     */

    private String title;
    private String url;

    /** <br> parcelable. */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    public Tag() {
    }

    protected Tag(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel source) {
            return new Tag(source);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    /** <br> interface. */


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
