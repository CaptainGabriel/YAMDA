package com.dev.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.dev.moviedb.model.dto.GenreDto;

import java.util.List;

/**
 * This class represents the list of genres a certain movie can have.
 */
public class Genres implements Parcelable {

    private List<GenreDto> mGenresNames;

    public Genres(List<GenreDto> mGenresNames) {
        this.mGenresNames = mGenresNames;
    }

    public List<GenreDto> getGenresNames() {
        return mGenresNames;
    }

    public String representation(){
        String delim = "", toConcat = "";
        for (GenreDto gName : getGenresNames()) {
            toConcat = toConcat.concat(delim).concat(gName.getName());
            delim = ",";
        }
        return toConcat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mGenresNames);
    }

    protected Genres(Parcel in) {
        this.mGenresNames = in.createTypedArrayList(GenreDto.CREATOR);
    }

    public static final Parcelable.Creator<Genres> CREATOR = new Parcelable.Creator<Genres>() {
        public Genres createFromParcel(Parcel source) {
            return new Genres(source);
        }

        public Genres[] newArray(int size) {
            return new Genres[size];
        }
    };
}
