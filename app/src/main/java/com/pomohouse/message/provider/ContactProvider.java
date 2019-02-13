package com.pomohouse.message.provider;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pomohouse.message.model.ContactDAO;
import com.pomohouse.message.model.ContactData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 9/15/2016 AD.
 */
public class ContactProvider {

    private Context context;
    private AppCompatActivity appCompatActivity;

    public ContactProvider(AppCompatActivity act) {
        this.appCompatActivity = act;
        this.context = act;
    }

    public void onContactListProvider(final OnLoadContactListener listener) {
        appCompatActivity.getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(context, POMOProviderConfig.ContactEntry.CONTENT_URI, null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
                try {
                    c.moveToFirst();
                    ContactDAO contactDAO = new ContactDAO();
                    List<ContactData> list = new ArrayList<>();
                    while (!c.isAfterLast()) {
                        ContactData d = new ContactData();
                        d.setContactId(c.getString(c.getColumnIndex(POMOProviderConfig.ContactEntry.CONTACT_ID)));
                        d.setName(c.getString(c.getColumnIndex(POMOProviderConfig.ContactEntry.NAME)));
                        d.setAvatar(c.getString(c.getColumnIndex(POMOProviderConfig.ContactEntry.AVATAR)));
                        d.setGender(c.getString(c.getColumnIndex(POMOProviderConfig.ContactEntry.GENDER)));
                        d.setPhone(c.getString(c.getColumnIndex(POMOProviderConfig.ContactEntry.PHONE)));
                        d.setAvatarType(c.getInt(c.getColumnIndex(POMOProviderConfig.ContactEntry.AVATAR_TYPE)));
                        d.setContactType(c.getString(c.getColumnIndex(POMOProviderConfig.ContactEntry.CONTACT_TYPE)));
                        d.setHaveNewMsg("F");
                        if (!d.getContactType().equalsIgnoreCase("other"))
                            list.add(d);
                        c.moveToNext();
                    }
                    contactDAO.setContactData(list);

                    if (listener != null)
                        listener.success(contactDAO);

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
        void success(ContactDAO collection);

        void error(Exception e);
    }
}
