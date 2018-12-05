package com.pomohouse.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pomohouse.message.interfaceclass.MessageReceiveListener;

/**
 * Created by SITTIPONG on 29/9/2559.
 */

public class MessageReceiver extends BroadcastReceiver {

    private MessageReceiveListener messageListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "New Message", Toast.LENGTH_LONG).show();

        if (messageListener != null)
            messageListener.receive(null);
    }

    public void setMessageReceiveListener(MessageReceiveListener messageListener) {
        this.messageListener = messageListener;
    }
}
