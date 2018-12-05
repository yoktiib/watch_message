package com.pomohouse.message.global;

/**
 * Created by SITTIPONG on 4/9/2559.
 */
public class ObjectDataInstance {

    private static volatile ObjectDataInstance instance = null;

    private String myImei;
    private int apiLoopTime;

    public static synchronized ObjectDataInstance getInstance() {
        if (instance == null)
            instance = new ObjectDataInstance();
        return instance;
    }

    public String getMyImei() {
        return myImei;
    }

    public void setMyImei(String myImei) {
        this.myImei = myImei;
    }

    public int getApiLoopTime() {
        return apiLoopTime;
    }

    public void setApiLoopTime(int apiLoopTime) {
        this.apiLoopTime = apiLoopTime;
    }

}
