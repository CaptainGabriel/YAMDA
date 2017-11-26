package com.dev.moviedb.mvvm.model.movies;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;

import com.dev.moviedb.utils.DateUtils;

import org.joda.time.DateTime;

import petegabriel.com.yamda.R;

/**
 * This class represents data that describes a certain movie in a more detailed way.
 */
public class AdvancedFact implements Parcelable {

    private String mImdbID;
    private int mRuntime;
    private String mTagLine;

    private String mHomepage;
    private Genres mGenres;

    private DateTime currentDate;
    private String releaseDate;

    public AdvancedFact(String mImdbID, int mRuntime, String mTagLine, String releaseDate, String mHomepage,
                        Genres mGenres) {
        this.mImdbID = mImdbID;
        this.mRuntime = mRuntime;
        this.mTagLine = mTagLine;
        this.mHomepage = mHomepage;
        this.mGenres = mGenres;
        this.releaseDate = releaseDate;
        currentDate = new DateTime();
    }

    public String getReadableStatus(Application app) {

        DateTime release = DateUtils.buildDate(releaseDate);
        String releasedState = app.getResources().getString(R.string.details_movie_release_state);
        String inProductionState = app.getResources().getString(R.string.details_movie_production_state);
        return (currentDate.isBefore(release.toInstant())) ? inProductionState : releasedState;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public String getImdbID() {
        return mImdbID;
    }



    public String getTagLine() {
        return mTagLine;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public Genres getGenres() {
        return mGenres;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mImdbID);
        dest.writeInt(this.mRuntime);
        dest.writeString(this.mTagLine);
        dest.writeString(this.mHomepage);
        dest.writeParcelable(this.mGenres, 0);
        dest.writeSerializable(this.currentDate);
        dest.writeString(this.releaseDate);
    }

    protected AdvancedFact(Parcel in) {
        this.mImdbID = in.readString();
        this.mRuntime = in.readInt();
        this.mTagLine = in.readString();
        this.mHomepage = in.readString();
        this.mGenres = in.readParcelable(Genres.class.getClassLoader());
        this.currentDate = (DateTime) in.readSerializable();
        this.releaseDate = in.readString();
    }

    public static final Creator<AdvancedFact> CREATOR = new Creator<AdvancedFact>() {
        public AdvancedFact createFromParcel(Parcel source) {
            return new AdvancedFact(source);
        }

        public AdvancedFact[] newArray(int size) {
            return new AdvancedFact[size];
        }
    };
}
