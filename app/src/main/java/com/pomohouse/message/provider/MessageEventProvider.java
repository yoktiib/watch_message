package com.pomohouse.message.provider;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.pomohouse.message.model.MessageNewData;
import com.pomohouse.message.tools.EventCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 10/5/2016 AD.
 */

public class MessageEventProvider {
    private Context context;
    private AppCompatActivity appCompatActivity;

    public MessageEventProvider(AppCompatActivity act) {
        this.appCompatActivity = act;
        this.context = act;
    }

    public void onNewMessageProvider(final OnLoadMessageEventListener listener) {
        appCompatActivity.getLoaderManager().initLoader(2, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(context,
                        EventEntry.CONTENT_URI, null, EventEntry.EVENT_CODE + " = ?", new String[]{EventCode.MESSAGE},
                        null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
                try {
                    List<MessageNewData> data = new ArrayList<>();
                    MessageNewData m;
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        m = new Gson().fromJson(c.getString(c.getColumnIndex("content")), MessageNewData.class);
                        data.add(m);
                        c.moveToNext();
                    }
                    listener.success(data);
                } catch (Exception e) {
                    listener.error(e);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> arg0) {

            }
        });
    }

    public void deleteNewMessage() {
        appCompatActivity.getContentResolver()
                .delete(EventEntry.CONTENT_URI, EventEntry.EVENT_CODE + " = ?", new String[]{EventCode.MESSAGE});
    }

    public interface OnLoadMessageEventListener {
        void success(List<MessageNewData> data);

        void error(Exception e);
    }
}
