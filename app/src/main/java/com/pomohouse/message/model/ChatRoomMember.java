package com.pomohouse.message.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Art-ars on 9/11/2016.
 */
public class ChatRoomMember implements Parcelable {

    @SerializedName("lastMessageId")
    @Expose
    private Integer lastMessageID;
    @SerializedName("member")
    @Expose
    private String member;
    @SerializedName("memberType")
    @Expose
    private Integer memberType;

    public ChatRoomMember() {
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getLastMessageID() {
        return lastMessageID;
    }

    public void setLastMessageID(Integer lastMessageID) {
        this.lastMessageID = lastMessageID;
    }


    protected ChatRoomMember(Parcel in) {
        lastMessageID = in.readByte() == 0x00 ? null : in.readInt();
        member = in.readString();
        memberType = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (lastMessageID == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(lastMessageID);
        }
        dest.writeString(member);
        if (memberType == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(memberType);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ChatRoomMember> CREATOR = new Parcelable.Creator<ChatRoomMember>() {
        @Override
        public ChatRoomMember createFromParcel(Parcel in) {
            return new ChatRoomMember(in);
        }

        @Override
        public ChatRoomMember[] newArray(int size) {
            return new ChatRoomMember[size];
        }
    };
}