package com.example.ricardopeixe.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents the data that describes the popularity of a certain
 * movie.
 */
public class Popularity implements Parcelable {

    private double mVoteAverage;
    private double mPopularity;
    private int mVoteCount;

    public Popularity(double mVoteAverage, double mPopularity, int mVoteCount) {
        this.mVoteAverage = mVoteAverage;
        this.mPopularity = mPopularity;
        this.mVoteCount = mVoteCount;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }


    @Override
    public String toString() {
        return "Popularity: " + mPopularity +
                " Count: " + mVoteCount +
                " Average: " +mVoteAverage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.mVoteAverage);
        dest.writeDouble(this.mPopularity);
        dest.writeInt(this.mVoteCount);
    }

    protected Popularity(Parcel in) {
        this.mVoteAverage = in.readDouble();
        this.mPopularity = in.readDouble();
        this.mVoteCount = in.readInt();
    }

    public static final Parcelable.Creator<Popularity> CREATOR = new Parcelable.Creator<Popularity>() {
        public Popularity createFromParcel(Parcel source) {
            return new Popularity(source);
        }

        public Popularity[] newArray(int size) {
            return new Popularity[size];
        }
    };
}



