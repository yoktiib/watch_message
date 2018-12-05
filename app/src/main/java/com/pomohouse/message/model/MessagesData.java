package com.pomohouse.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Art-ars on 9/10/2016.
 */
public class MessagesData extends RealmObject {
    @PrimaryKey
    @SerializedName("messageId")
    @Expose
    private int messageId;
    @SerializedName("chatRoomId")
    @Expose
    private int chatRoomId;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("senderType")
    @Expose
    private int senderType;
    @SerializedName("stickerId")
    @Expose
    private String stickerId;
    @SerializedName("voiceLength")
    @Expose
    private int voiceLength;
    @SerializedName("voicePath")
    @Expose
    private String voicePath;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;
    @SerializedName("messageType")
    @Expose
    private int messageType;

    private int msgTypeForView;

    public int getVoiceLength() {
        return voiceLength;
    }

    public void setVoiceLength(int voiceLength) {
        this.voiceLength = voiceLength;
    }

    public int getMsgTypeForView() {
        return msgTypeForView;
    }

    public void setMsgTypeForView(int msgTypeForView) {
        this.msgTypeForView = msgTypeForView;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public Object getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getSenderType() {
        return senderType;
    }

    public void setSenderType(int senderType) {
        this.senderType = senderType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
