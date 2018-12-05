package com.pomohouse.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mac on 10/12/2016 AD.
 */

public class ChatRoomTemp extends RealmObject {
    @SerializedName("memberId")
    @Expose
    private String memberId;
    @SerializedName("chatRoomId")
    @Expose
    private Integer chatRoomId;

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

}
