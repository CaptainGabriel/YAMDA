package com.example.dev.moviedb.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Dto class to represent the information about an youtube video.
 *
 * @version 1.0.0
 */
public class YoutubeDto implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("type")
    @Expose
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.size);
        dest.writeString(this.source);
        dest.writeString(this.type);
    }

    public YoutubeDto() {
    }

    protected YoutubeDto(Parcel in) {
        this.name = in.readString();
        this.size = in.readString();
        this.source = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<YoutubeDto> CREATOR = new Parcelable.Creator<YoutubeDto>() {
        public YoutubeDto createFromParcel(Parcel source) {
            return new YoutubeDto(source);
        }

        public YoutubeDto[] newArray(int size) {
            return new YoutubeDto[size];
        }
    };
}