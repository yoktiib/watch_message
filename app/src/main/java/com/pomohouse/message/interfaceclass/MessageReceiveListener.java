package com.pomohouse.message.interfaceclass;

import com.pomohouse.message.model.MessagesData;

/**
 * Created by SITTIPONG on 4/10/2559.
 */

public interface MessageReceiveListener {
    void receive(MessagesData data);
}
