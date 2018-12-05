package com.pomohouse.message.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 2/7/2017 AD.
 */

public class ConfigsDao implements Parcelable {

    /**
     * messageTime : 5
     */
    @SerializedName("messageTime")
    @Expose
    private int messageTime;

    public int getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(int messageTime) {
        this.messageTime = messageTime;
    }

    protected ConfigsDao(Parcel in) {
        messageTime = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ConfigsDao> CREATOR = new Parcelable.Creator<ConfigsDao>() {
        @Override
        public ConfigsDao createFromParcel(Parcel in) {
            return new ConfigsDao(in);
        }

        @Override
        public ConfigsDao[] newArray(int size) {
            return new ConfigsDao[size];
        }
    };
}