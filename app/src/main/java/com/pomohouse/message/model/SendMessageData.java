package com.pomohouse.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Art-ars on 9/11/2016.
 */
public class SendMessageData {

    @SerializedName("chatRoomId")
    @Expose
    private Integer chatRoomId;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("stickerId")
    @Expose
    private String stickerId;
    @SerializedName("voicePath")
    @Expose
    private Object voicePath;
    @SerializedName("text")
    @Expose
    private Object text;
    @SerializedName("messageType")
    @Expose
    private Integer messageType;
    @SerializedName("voiceLength")
    @Expose
    private Integer voiceLength;
    @SerializedName("messageId")
    @Expose
    private Integer messageId;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
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

    public void setVoicePath(Object voicePath) {
        this.voicePath = voicePath;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public Integer getVoiceLength() {
        return voiceLength;
    }

    public void setVoiceLength(Integer voiceLength) {
        this.voiceLength = voiceLength;
    }

}
