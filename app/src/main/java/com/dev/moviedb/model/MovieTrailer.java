package com.dev.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the trailers associated with a certain movie.
 */
public class MovieTrailer implements Parcelable {

    private static final String YOUTUBE_HOST = "https://www.youtube.com/watch?v=%s&amp;feature=youtube_gdata";

    private List<String> mTrailersLink;

    public MovieTrailer(){
        mTrailersLink = new ArrayList<>();
    }

    public void addTrailer(String path){
        String url = String.format(YOUTUBE_HOST, path);
        mTrailersLink.add(url);
    }

    public List<String> getTrailersLink() {
        return mTrailersLink;
    }

    /*
    "trailers": {
        "quicktime": [],
        "youtube": [
          {
            "name": "Ant-Man Official Trailer #1",
            "size": "HD",
            "source": "ZiS7akYy4yA",
            "type": "Trailer"
          }
        ]
    }
     */


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.mTrailersLink);
    }

    protected MovieTrailer(Parcel in) {
        this.mTrailersLink = in.createStringArrayList();
    }

    public static final Parcelable.Creator<MovieTrailer> CREATOR = new Parcelable.Creator<MovieTrailer>() {
        public MovieTrailer createFromParcel(Parcel source) {
            return new MovieTrailer(source);
        }

        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
}
