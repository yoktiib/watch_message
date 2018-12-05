
package com.pomohouse.message.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllContactDAO {

    @SerializedName("resCode")
    @Expose
    public Integer resCode;
    @SerializedName("resDesc")
    @Expose
    public String resDesc;

    public List<AllContactData> getData() {
        return data;
    }

    public void setData(List<AllContactData> data) {
        this.data = data;
    }

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

    @SerializedName("data")
    @Expose
    public List<AllContactData> data = new ArrayList<AllContactData>();

}
