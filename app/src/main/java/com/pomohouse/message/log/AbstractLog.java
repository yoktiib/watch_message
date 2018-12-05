package com.pomohouse.message.log;

import android.util.Log;

/**
 * Created by SITTIPONG on 31/8/2559.
 */
public class AbstractLog {

    private static boolean isShowLog = false;

    public static void e(String tag, String msg) {
        if (isShowLog)
            Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isShowLog)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isShowLog)
            Log.w(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isShowLog)
            Log.d(tag, msg);
    }
}
