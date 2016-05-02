package com.example.dev.moviedb.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO class that represents the trailers object inside an instance of {@link MovieDTO}.
 *
 * @version 1.0.0
 */
public class TrailersDto implements Parcelable {

    private List<YoutubeDto> youtube = new ArrayList<YoutubeDto>();

    public List<YoutubeDto> getYoutube() {
        return youtube;
    }

    /**
     *
     * @param youtube
     * The youtube
     */
    public void setYoutube(List<YoutubeDto> youtube) {
        this.youtube = youtube;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(youtube);
    }

    public TrailersDto() {
    }

    protected TrailersDto(Parcel in) {
        this.youtube = in.createTypedArrayList(YoutubeDto.CREATOR);
    }

    public static final Parcelable.Creator<TrailersDto> CREATOR = new Parcelable.Creator<TrailersDto>() {
        public TrailersDto createFromParcel(Parcel source) {
            return new TrailersDto(source);
        }

        public TrailersDto[] newArray(int size) {
            return new TrailersDto[size];
        }
    };
}