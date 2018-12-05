package com.pomohouse.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Art-ars on 9/11/2016.
 */
public class SendMessageDAO {

    @SerializedName("resCode")
    @Expose
    private Integer resCode;
    @SerializedName("resDesc")
    @Expose
    private String resDesc;
    @SerializedName("data")
    @Expose
    private SendMessageData data;

    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public SendMessageData getData() {
        return data;
    }

    public void setData(SendMessageData data) {
        this.data = data;
    }

}
