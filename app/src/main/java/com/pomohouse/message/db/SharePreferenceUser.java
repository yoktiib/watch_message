package com.pomohouse.message.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUser {

//	public static final String TOKEN_ID = "token_id";
//	public static final String TOKEN_ID_OFFICER = "token_id_officer";
//	public static final String OFFICER_ID = "officer_id";
//	public static final String USERNAME_LOGIN = "username_login";
//	public static final String FLAG_LOGIN = "flag_login";
	public static final String DEFAULT_LANGUAGE = "default_language";
	public static final String CHATROOM_ID = "CHATROOM_ID_";

    public static final String PREF_NAME = "WAFFLE_PERSON_CHAT";
    public static final int MODE = Context.MODE_PRIVATE;

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

}

//		SharePreferenceUser.writeString(ctx, "id", "");
// 		String id = SharePreferenceUser.readString(ctx, "id", "null");




