package com.pomohouse.message.receiver;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by SITTIPONG on 29/9/2559.
 */

public class MessageIntentService extends IntentService {


    public final static String EVENT_MESSAGE_INTENT = "com.pomohouse.service.EVENT_MESSAGE_INTENT";
    //    public final static String EVENT_GROUP_CHAT_INTENT = "com.pomohouse.service.EVENT_GROUP_CHAT_INTENT";

    public MessageIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
