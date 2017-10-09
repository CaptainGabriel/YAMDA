package com.dev.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents the most simple data that is always available related
 * to a certain movie.
 */
public class PrimaryFact implements Parcelable {

    private String mOverview;
    private String mOriginalTitle;
    private String mTitle;
    private Long mId;
    private String mReleaseDate;


    public PrimaryFact(String mOverview, String mOriginalTitle, String mTitle, Long mId, String mReleaseDate) {
        this.mOverview = mOverview;
        this.mOriginalTitle = mOriginalTitle;
        this.mTitle = mTitle;
        this.mId = mId;
        this.mReleaseDate = mReleaseDate;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public Long getId() {
        return mId;
    }

    @Override
    public String toString() {
        return "(" + mId + ") " + mOriginalTitle + " - " + mReleaseDate ;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mOverview);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mTitle);
        dest.writeValue(this.mId);
        dest.writeString(this.mReleaseDate);
    }

    protected PrimaryFact(Parcel in) {
        this.mOverview = in.readString();
        this.mOriginalTitle = in.readString();
        this.mTitle = in.readString();
        this.mId = (Long) in.readValue(Long.class.getClassLoader());
        this.mReleaseDate = in.readString();
    }

    public static final Parcelable.Creator<PrimaryFact> CREATOR = new Parcelable.Creator<PrimaryFact>() {
        public PrimaryFact createFromParcel(Parcel source) {
            return new PrimaryFact(source);
        }

        public PrimaryFact[] newArray(int size) {
            return new PrimaryFact[size];
        }
    };
}
