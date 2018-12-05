package com.pomohouse.message;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by administrator on 3/28/2016 AD.
 */

public class WearerInfoUtils {
    private String imei;
    private boolean haveSimCard;
    private static WearerInfoUtils ourInstance = new WearerInfoUtils();

    public WearerInfoUtils() {
    }

    public static WearerInfoUtils getInstance() {
        return ourInstance;
    }

    public WearerInfoUtils initWearerInfoUtils(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getImei();
        haveSimCard = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
        return this;
    }

    public boolean isHaveSimCard() {
        return haveSimCard;
    }

    public String getImei() {
        return imei;
    }
}