package com.dev.moviedb.storage.repo.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.dev.moviedb.mvvm.model.movies.content.Identifiable;

import org.joda.time.DateTime;

/**
 * Represents the records inside the three main tables of the database.
 */
public class DataRecord implements Identifiable, Parcelable {

    //primary facts
    private String overview;
    private String originalTitle;
    private Long id;
    private String releaseDate;

    //paths
    private String backdropImagePath;
    private String posterImagePath;
    private double voteAverage;

    private boolean favorite;
    private long createdAt;
    private String mImdbId;
    private int mRuntime;
    private String mStatus;
    private String mTagline;
    private String mGenres;
    private String mTrailerUrl;

    public DataRecord(){
        this.overview = null;
        this.originalTitle = null;
        this.id = -1L;
        this.releaseDate = null;

        this.backdropImagePath = null;
        this.posterImagePath = null;
        this.voteAverage = 0.0;

        this.favorite = false;
        this.createdAt = DateTime.now().getMillis(); //System.currentTimeMillis()

        mTrailerUrl = null;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Long getId() {
        return id;
    }

    @Override
    public long resolveID() {
        return getId();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropImagePath() {
        return backdropImagePath;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setBackdropImagePath(String backdropImagePath) {
        this.backdropImagePath = backdropImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public void setTrailerUrl(String trailerUrl) {
        mTrailerUrl = trailerUrl;
    }

    public String getTrailerUrl() {
        return mTrailerUrl;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = (favorite == 0) ? false : true;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setImdbId(String imdbId) {
        mImdbId = imdbId;
    }

    public void setRuntime(int runtime) {
        mRuntime = runtime;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public void setTagline(String tagline) {
        mTagline = tagline;
    }

    public void setGenres(String genres) {
        mGenres = genres;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public String getGenres() {
        return mGenres;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getTagline() {
        return mTagline;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.originalTitle);
        dest.writeValue(this.id);
        dest.writeString(this.releaseDate);
        dest.writeString(this.backdropImagePath);
        dest.writeString(this.posterImagePath);
        dest.writeDouble(this.voteAverage);
        dest.writeByte(favorite ? (byte) 1 : (byte) 0);
        dest.writeLong(this.createdAt);
        dest.writeString(this.mImdbId);
        dest.writeInt(this.mRuntime);
        dest.writeString(this.mStatus);
        dest.writeString(this.mTagline);
        dest.writeString(this.mGenres);
        dest.writeString(this.mTrailerUrl);
    }

    protected DataRecord(Parcel in) {
        this.overview = in.readString();
        this.originalTitle = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.releaseDate = in.readString();
        this.backdropImagePath = in.readString();
        this.posterImagePath = in.readString();
        this.voteAverage = in.readDouble();
        this.favorite = in.readByte() != 0;
        this.createdAt = in.readLong();
        this.mImdbId = in.readString();
        this.mRuntime = in.readInt();
        this.mStatus = in.readString();
        this.mTagline = in.readString();
        this.mGenres = in.readString();
        this.mTrailerUrl = in.readString();
    }

    public static final Creator<DataRecord> CREATOR = new Creator<DataRecord>() {
        public DataRecord createFromParcel(Parcel source) {
            return new DataRecord(source);
        }

        public DataRecord[] newArray(int size) {
            return new DataRecord[size];
        }
    };
}
