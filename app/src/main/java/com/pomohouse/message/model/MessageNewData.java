package com.pomohouse.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by SITTIPONG on 8/10/2559.
 */

public class MessageNewData extends RealmObject {
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;

    public int getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @SerializedName("chatRoomId")
    @Expose
    private int chatRoomId;
    @SerializedName("messageId")
    @Expose
    private int messageId;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
