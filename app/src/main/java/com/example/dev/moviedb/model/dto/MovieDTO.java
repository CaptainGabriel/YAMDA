package com.example.dev.moviedb.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO that represents the concept of movie held by the Api.
 *
 * @version 0.0.1
 */
public class MovieDTO implements Parcelable {

    /**
     * The default values, returned whenever a given movie parameter is not available.
     */
    private static final String EMPTY_STRING = "";

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath; /** The backdrop image path */

    /**
     * The list of genres
     */
    private List<GenreDto> genres = new ArrayList<>();

    /**
     * The movie id
     */
    private Integer id;

    /**
     * The main overview
     */
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    /**
     * The release date
     */

    @SerializedName("poster_path")
    @Expose
    private String posterPath; /** The poster path */

    /**
     * Movie's popularity
     */
    private Double popularity;

    /**
     * The movie title
     */
    private String title;

    /**
     * If has video or not
     */
    private Boolean video;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    /**
     * The current status
     */
    private String status;

    /**
     * The runtime length
     */
    private int runtime;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    private String homepage;

    private int vote_count;

    private TrailersDto trailers;


    /**
     * ctor.
     */
    public MovieDTO() { }


    /**
     *
     * @return
     * Poster path
     */
    public String getPosterPath() {
        return posterPath == null ? EMPTY_STRING : posterPath.substring(1);
    }

    /**
     *
     * @return
     * Backdrop image path
     */
    public String getImageBackdropPath() {
        return backdropPath == null ? EMPTY_STRING : backdropPath.substring(1);
    }

    /**
     *
     * @return
     * Status of the movie
     */
    public String getStatus() {
        return status;
    }


    public int getVoteCount() {
        return vote_count;
    }

    public String getHomepage() {
        return homepage;
    }

    /**
     *
     * @return
     * The genre ids
     */
    public List<GenreDto> getGenres() {
        return genres;
    }

    /**
     *
     * @return
     * The runtime
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return
     * The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     *
     * @return
     * The release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     * The vote average
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getTagline() {
        return tagline;
    }

    public String getImdb() {
        return imdbId;
    }

    public TrailersDto getTrailers() {
        return trailers;
    }

    /**
     * Provides a printable representation of this object.
     */
    @Override
    public String toString() {
        return new StringBuilder("MovieDTO{")
                .append("backdropPath='")
                .append(backdropPath).append('\'')
                .append(", genreIds=").append(genres)
                .append(", id=").append(id)
                .append(", imdbID=").append(imdbId)
                .append(", tagline='").append(tagline).append('\'')
                .append(", overview='").append(overview).append('\'')
                .append(", releaseDate='").append(releaseDate).append('\'')
                .append(", posterPath='").append(posterPath).append('\'')
                .append(", popularity=").append(popularity)
                .append(", title='").append(title).append('\'')
                .append(", video=").append(video)
                .append(", voteAverage=").append(voteAverage)
                .append(", status='").append(status).append('\'')
                .append(", runtime='").append(runtime).append('\'')
                .append('}').toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backdropPath);
        dest.writeTypedList(genres);
        dest.writeValue(this.id);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeValue(this.popularity);
        dest.writeString(this.title);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.status);
        dest.writeInt(this.runtime);
        dest.writeString(this.tagline);
        dest.writeString(this.imdbId);
        dest.writeString(this.homepage);
        dest.writeInt(this.vote_count);
        dest.writeParcelable(this.trailers, 0);
    }

    protected MovieDTO(Parcel in) {
        this.backdropPath = in.readString();
        this.genres = in.createTypedArrayList(GenreDto.CREATOR);
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.title = in.readString();
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.status = in.readString();
        this.runtime = in.readInt();
        this.tagline = in.readString();
        this.imdbId = in.readString();
        this.homepage = in.readString();
        this.vote_count = in.readInt();
        this.trailers = in.readParcelable(TrailersDto.class.getClassLoader());
    }

    public static final Creator<MovieDTO> CREATOR = new Creator<MovieDTO>() {
        public MovieDTO createFromParcel(Parcel source) {
            return new MovieDTO(source);
        }

        public MovieDTO[] newArray(int size) {
            return new MovieDTO[size];
        }
    };
}
