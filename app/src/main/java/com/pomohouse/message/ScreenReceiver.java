package com.pomohouse.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pomohouse.message.interfaceclass.ScreenReceiveListener;

/**
 * Created by SITTIPONG on 5/4/2560.
 */

public class ScreenReceiver extends BroadcastReceiver {

    private ScreenReceiveListener listener;

    public ScreenReceiver(ScreenReceiveListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
//            Log.w("ACTION_SCREEN_OFF", "ACTION_SCREEN_OFF");
            if (listener != null)
                listener.screenOff();
        }
    }

    public void removeListener() {
        this.listener = null;
    }
}
