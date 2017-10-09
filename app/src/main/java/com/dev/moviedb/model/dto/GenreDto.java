package com.dev.moviedb.model.dto;


import android.os.Parcel;
import android.os.Parcelable;


public class GenreDto implements Parcelable {
    private Integer id;
    private String name;


    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public GenreDto() {
    }

    protected GenreDto(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Parcelable.Creator<GenreDto> CREATOR = new Parcelable.Creator<GenreDto>() {
        public GenreDto createFromParcel(Parcel source) {
            return new GenreDto(source);
        }

        public GenreDto[] newArray(int size) {
            return new GenreDto[size];
        }
    };
}
