
package com.pomohouse.message.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllContactData implements Parcelable {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("imei")
    @Expose
    public String imei;
    @SerializedName("memberId")
    @Expose
    public Integer memberId;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("avatar")
    @Expose
    public String avatar;

    protected AllContactData(Parcel in) {
        name = in.readString();
        imei = in.readString();
        phone = in.readString();
        avatar = in.readString();
        type = in.readString();
        memberId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imei);
        dest.writeString(phone);
        dest.writeString(avatar);
        dest.writeString(type);
        dest.writeInt(memberId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllContactData> CREATOR = new Creator<AllContactData>() {
        @Override
        public AllContactData createFromParcel(Parcel in) {
            return new AllContactData(in);
        }

        @Override
        public AllContactData[] newArray(int size) {
            return new AllContactData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("contactType")
    @Expose
    public String type;

}
