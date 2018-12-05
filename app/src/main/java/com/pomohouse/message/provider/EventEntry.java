package com.pomohouse.message.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by SITTIPONG on 4/10/2559.
 */
public class EventEntry implements BaseColumns {

    private static final String CONTENT_AUTHORITY = "com.pomohouse";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    static final String TB_EVENT = "TB_EVENT";
    public static final String EVENT_ID = "EVENT_ID";
    public static final String EVENT_CODE = "EVENT_CODE";
    public static final String EVENT_TYPE = "EVENT_TYPE";
    public static final String SENDER = "SENDER";
    public static final String RECEIVE = "RECEIVE";
    public static final String SENDER_INFO = "SENDER_INFO";
    public static final String RECEIVE_INFO = "RECEIVE_INFO";
    public static final String CONTENT = "CONTENT";
    public static final String TIME_STAMP = "TIME_STAMP";
    public static final String STATUS = "STATUS";
    public static final String PATH_EVENT = "event";

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();

    static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_EVENT;/*
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CONTACT;*/

    static Uri buildEventUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}