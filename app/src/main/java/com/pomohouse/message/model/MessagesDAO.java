package com.pomohouse.message.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Art-ars on 9/10/2016.
 */
public class MessagesDAO {

    @SerializedName("resCode")
    @Expose
    private Integer resCode;
    @SerializedName("resDesc")
    @Expose
    private String resDesc;
    @SerializedName("data")
    @Expose
    private List<MessagesData> data = new ArrayList<MessagesData>();

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

    public List<MessagesData> getData() {
        return data;
    }

    public void setData(List<MessagesData> data) {
        this.data = data;
    }

}
