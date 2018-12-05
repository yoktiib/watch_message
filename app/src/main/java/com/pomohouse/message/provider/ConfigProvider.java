package com.pomohouse.message.provider;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.model.ConfigModel;

/**
 * Created by mac on 9/15/2016 AD.
 */
public class ConfigProvider {

    private Context context;
    private AppCompatActivity appCompatActivity;

    public ConfigProvider(AppCompatActivity act) {
        this.appCompatActivity = act;
        this.context = act;
    }

    public void onConfigProvider(final OnLoadContactListener listener) {
        appCompatActivity.getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(context, POMOProviderConfig.WearerEntry.CONTENT_URI, null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
                try {
                    c.moveToFirst();

                    AbstractLog.e("อีมี่จาก DB ลันเชอร์", c.getString(c.getColumnIndex(POMOProviderConfig.WearerEntry.IMEI)));
                    ConfigModel m = new ConfigModel();
                    m.setImei(c.getString(c.getColumnIndex(POMOProviderConfig.WearerEntry.IMEI)));

                    if (listener != null)
                        listener.success(m);

                } catch (RuntimeException re) {
                    listener.error(re);
                } catch (Exception e) {
                    listener.error(e);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> arg0) {

            }
        });
    }

    public interface OnLoadContactListener {
        void success(ConfigModel config);
        void error(Exception e);
    }
}
