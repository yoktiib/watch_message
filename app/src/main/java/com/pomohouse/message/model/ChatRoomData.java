package com.pomohouse.message.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pomohouse.message.config.Config;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomData implements Parcelable {
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("messageMember")
    @Expose
    private String messageMember;
    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("chatRoomId")
    @Expose
    private int chatRoomId;
    @SerializedName("chatRoomType")
    @Expose
    private int chatRoomType;
    @SerializedName("config")
    @Expose
    private ConfigsDao config;
    @SerializedName("chatRoomMember")
    @Expose
    private List<ChatRoomMember> chatRoomMember = new ArrayList<>();

    public ChatRoomData() {

    }

    protected ChatRoomData(Parcel in) {
        ownerId = in.readString();
        imei = in.readString();
        messageMember = in.readString();
        chatRoomId = in.readInt();
        chatRoomType = in.readInt();
        config = (ConfigsDao) in.readValue(Config.class.getClassLoader());
        if (in.readByte() == 0x01) {
            chatRoomMember = new ArrayList<ChatRoomMember>();
            in.readList(chatRoomMember, ChatRoomMember.class.getClassLoader());
        } else {
            chatRoomMember = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chatRoomId);
        dest.writeInt(chatRoomType);
        dest.writeString(imei);
        dest.writeString(ownerId);
        dest.writeString(messageMember);
        dest.writeValue(config);
        if (chatRoomMember == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(chatRoomMember);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ChatRoomData> CREATOR = new Parcelable.Creator<ChatRoomData>() {
        @Override
        public ChatRoomData createFromParcel(Parcel in) {
            return new ChatRoomData(in);
        }

        @Override
        public ChatRoomData[] newArray(int size) {
            return new ChatRoomData[size];
        }
    };

    public int getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public int getChatRoomType() {
        return chatRoomType;
    }

    public void setChatRoomType(int chatRoomType) {
        this.chatRoomType = chatRoomType;
    }

    public ConfigsDao getConfig() {
        return config;
    }

    public void setConfig(ConfigsDao config) {
        this.config = config;
    }

    public List<ChatRoomMember> getChatRoomMember() {
        return chatRoomMember;
    }

    public void setChatRoomMember(List<ChatRoomMember> chatRoomMember) {
        this.chatRoomMember = chatRoomMember;
    }
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMessageMember() {
        return messageMember;
    }

    public void setMessageMember(String messageMember) {
        this.messageMember = messageMember;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}