package com.example.dev.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents the images associated with a certain movie.
 *
 */
public class MovieImages implements Parcelable {

    private String mBackdropImagePath;
    private String mPosterImagePath;

    public MovieImages(String mBackdropImagePath, String mPosterImagePath) {
        this.mBackdropImagePath = mBackdropImagePath;
        this.mPosterImagePath = mPosterImagePath;
    }

    public String getBackdropImagePath() {
        return mBackdropImagePath;
    }

    public String getPosterImagePath() {
        return mPosterImagePath;
    }

    @Override
    public String toString() {
        return "BackdropImage Path: " + mBackdropImagePath +
                " - PosterImage Path: " + mPosterImagePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBackdropImagePath);
        dest.writeString(this.mPosterImagePath);
    }

    protected MovieImages(Parcel in) {
        this.mBackdropImagePath = in.readString();
        this.mPosterImagePath = in.readString();
    }

    public static final Parcelable.Creator<MovieImages> CREATOR = new Parcelable.Creator<MovieImages>() {
        public MovieImages createFromParcel(Parcel source) {
            return new MovieImages(source);
        }

        public MovieImages[] newArray(int size) {
            return new MovieImages[size];
        }
    };
}
