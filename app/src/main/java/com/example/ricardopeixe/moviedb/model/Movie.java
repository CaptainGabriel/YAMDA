package com.example.ricardopeixe.moviedb.model;

import org.joda.time.DateTime;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents the concept in the client side of the movie concept held by the Api.
 *
 */
public class Movie implements Parcelable, Identifiable {

    private PrimaryFact mPrimaryFacts;

    private MovieImages mMovieImages;

    private Popularity mPopularity;

    private AdvancedFact mAdvancedFacts;

    private MovieTrailer mMovieTrailers;


    public Movie(PrimaryFact mPrimaryFacts) {

        this.mPrimaryFacts = mPrimaryFacts;
    }


    public void setMovieImages(MovieImages movieImages) {
        mMovieImages = movieImages;
    }

    public void setMovieTrailers(MovieTrailer movieTrailers) {
        mMovieTrailers = movieTrailers;
    }

    public void setPopularity(Popularity popularity) {
        mPopularity = popularity;
    }

    public void setAdvancedFacts(AdvancedFact advancedFacts) {
        this.mAdvancedFacts = advancedFacts;
    }

    private long timestamp = System.currentTimeMillis();
    public boolean follow;

    @Override
    public String toString() {
        return "Movie \"" + this.mPrimaryFacts.getTitle() + "\"  with ID "
                + this.mPrimaryFacts.getId() ;
    }

    @Override
    public long resolveID() {
        return mPrimaryFacts.getId();
    }

    /**
     * If the current year equal to the year present at the release date,
     * we return the pair 'mm/dd'. Otherwise return only the year.
     *
     * This method serves only to show information in view. Should not
     * be used to make calculations or some sort of "business logic".
     *
     * @version 0.0.2
     */
    public String getReleaseDateFacade(){
        DateTime now = DateTime.now();
        DateTime releaseDate = DateTime.parse(this.mPrimaryFacts.getReleaseDate());
        if(releaseDate.getYear() < now.getYear() || releaseDate.getYear() > now.getYear())
            return String.valueOf(releaseDate.getYear());
        else return "" + releaseDate.monthOfYear().get() + "/" + releaseDate.dayOfMonth().get();
    }




    public PrimaryFact getPrimaryFacts() {
        return mPrimaryFacts;
    }

    public MovieImages getMovieImages() {
        return mMovieImages;
    }

    public Popularity getPopularity() {
        return mPopularity;
    }

    public AdvancedFact getAdvancedFacts() { return mAdvancedFacts; }


    public String getTrailer(){
        //just support one trailer. THe api dont support much more either.
        return (this.mMovieTrailers == null) ? null : this.mMovieTrailers.getTrailersLink().get(0);
    }

   @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mPrimaryFacts, flags);
        dest.writeParcelable(this.mMovieImages, flags);
        dest.writeParcelable(this.mPopularity, flags);
        dest.writeParcelable(this.mAdvancedFacts, flags);
        dest.writeParcelable(this.mMovieTrailers, flags);
        //dest.writeSerializable(this.currentDate);
        dest.writeLong(this.timestamp);
        dest.writeByte(follow ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.mPrimaryFacts = in.readParcelable(PrimaryFact.class.getClassLoader());
        this.mMovieImages = in.readParcelable(MovieImages.class.getClassLoader());
        this.mPopularity = in.readParcelable(Popularity.class.getClassLoader());
        this.mAdvancedFacts = in.readParcelable(AdvancedFact.class.getClassLoader());
        this.mMovieTrailers = in.readParcelable(MovieTrailer.class.getClassLoader());
        //this.currentDate = (DateTime) in.readSerializable();
        this.timestamp = in.readLong();
        this.follow = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
