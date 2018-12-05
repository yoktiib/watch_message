package com.pomohouse.message.config;

/**
 * Created by SITTIPONG on 30/8/2559.
 */
public class Config {
    //public static final String BASE_URL = "http://203.151.232.222:3000/v1.2/api/watch/";
    //public static final String BASE_URL = "http://api.pomowaffle.com/v1.2/api/watch/";
    //private var API_BASE_URL = "http://staging-translate.pomowaffle.com/api/"
//    public static String API = "api.pomowaffle.com";
//    public static final String BASE_URL = "https://" + API + "/v1.2/api/watch/";

    public static final String BASE_URL = "http://13.228.58.26:3000/v1.1/api/watch/";
    //view contactType message
    public static final int TYPE_MY_VOICE = 0;
    public static final int TYPE_MY_STICKER = 1;
    public static final int TYPE_FRIEND_VOICE = 2;
    public static final int TYPE_FRIEND_STICKER = 3;
    public static final int TYPE_FRIEND_TEXT = 4;

    public static String CONTRACT_PARAM = "contract";
    public static String CHAT_ROOM_PARAM = "chatRoom";
    public static String STICKER_PARAM = "sticker";
    public static String STICKER_ID_PARAM = "sticker_id";

    public static final String DATABASE_NAME = "message_chat";

    public static final int DELAY_DISMISS_DIALOG = 200;


    public static final class TypeMessage {
        //    0=sticker, 1=voice, 2=text
        //    กำหนดตาม API อย่าเปลี่ยน
        public static final int STICKER = 0;
        public static final int VOICE = 1;
        public static final int TEXT = 2;
    }

    public static final class TypeRelation {
        public static final String FAMILY = "family";
        public static final String FRIEND = "friend";
    }
}
