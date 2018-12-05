
package com.pomohouse.message.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ContactData extends RealmObject implements Parcelable {

    @SerializedName("contactId")
    @Expose
    public String contactId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("imei")
    @Expose
    public String imei;
    @SerializedName("memberId")
    @Expose
    public String memberId;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("avatar")
    @Expose
    public String avatar;
    @SerializedName("avatarType")
    @Expose
    public int avatarType;
    @SerializedName("contactType")
    @Expose
    public String contactType;
    @SerializedName("gender")
    @Expose
    public String gender;

    public String lastTimeStamp;

    public int chatRoomId;

    public int getAvatarType() {
        return avatarType;
    }

    public void setAvatarType(int avatarType) {
        this.avatarType = avatarType;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getLastTimeStamp() {
        return lastTimeStamp;
    }

    public void setLastTimeStamp(String lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }

    public String isHaveNewMsg() {
        return isHaveNewMsg;
    }

    public void setHaveNewMsg(String haveNewMsg) {
        isHaveNewMsg = haveNewMsg;
    }

    public static Creator<ContactData> getCREATOR() {
        return CREATOR;
    }

    public String isHaveNewMsg;

    public ContactData() {

    }

    protected ContactData(Parcel in) {
        contactId = in.readString();
        name = in.readString();
        imei = in.readString();
        memberId = in.readString();
        phone = in.readString();
        avatar = in.readString();
        gender = in.readString();
        contactType = in.readString();
        isHaveNewMsg = in.readString();
        chatRoomId = in.readInt();
        avatarType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactId);
        dest.writeString(name);
        dest.writeString(imei);
        dest.writeString(memberId);
        dest.writeString(phone);
        dest.writeString(avatar);
        dest.writeString(gender);
        dest.writeString(contactType);
        dest.writeString(isHaveNewMsg);
        dest.writeInt(chatRoomId);
        dest.writeInt(avatarType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactData> CREATOR = new Creator<ContactData>() {
        @Override
        public ContactData createFromParcel(Parcel in) {
            return new ContactData(in);
        }

        @Override
        public ContactData[] newArray(int size) {
            return new ContactData[size];
        }
    };

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
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

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String type) {
        this.contactType = type;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String id) {
        this.contactId = id;
    }
}
