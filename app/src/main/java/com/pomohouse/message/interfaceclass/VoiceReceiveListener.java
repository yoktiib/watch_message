package com.pomohouse.message.interfaceclass;

import com.pomohouse.message.model.MessagesData;

import java.io.File;

/**
 * Created by SITTIPONG on 4/10/2559.
 */

public interface VoiceReceiveListener {
    void voiceReceive(String tempFileName, File recordFile);
}
