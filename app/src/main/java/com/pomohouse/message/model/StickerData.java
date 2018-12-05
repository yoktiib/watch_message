package com.pomohouse.message.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SITTIPONG on 23/9/2559.
 */

public class StickerData implements Parcelable {

    public StickerData() {

    }

    protected StickerData(Parcel in) {
        id = in.readInt();
    }

    public static final Creator<StickerData> CREATOR = new Creator<StickerData>() {
        @Override
        public StickerData createFromParcel(Parcel in) {
            return new StickerData(in);
        }

        @Override
        public StickerData[] newArray(int size) {
            return new StickerData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getStickerImg() {
        return stickerImg;
    }

    public void setStickerImg(int stickerImg) {
        this.stickerImg = stickerImg;
    }

    private int stickerImg;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
    }
}
