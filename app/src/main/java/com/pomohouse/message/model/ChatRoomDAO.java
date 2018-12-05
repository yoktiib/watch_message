
package com.pomohouse.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatRoomDAO {

    @SerializedName("resCode")
    @Expose
    private int resCode;
    @SerializedName("resDesc")
    @Expose
    private String resDesc;
    @SerializedName("data")
    @Expose
    private ChatRoomData data;

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public ChatRoomData getData() {
        return data;
    }

    public void setData(ChatRoomData data) {
        this.data = data;
    }
}
