package com.pomohouse.message.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by FoxjungNB on 9/4/2016.
 */

public class ReceiveVoiceMessageModel extends RealmObject {
    @Required
    private String Id;
    private String typeMessage;
    private String msgContent;
    private String dateTime;
    private String from;
    private String fromId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}
