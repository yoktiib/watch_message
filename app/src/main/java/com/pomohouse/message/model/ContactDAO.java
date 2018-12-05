package com.pomohouse.message.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mac on 9/15/2016 AD.
 */
public class ContactDAO implements Parcelable {

    public ContactDAO() {

    }

    protected ContactDAO(Parcel in) {
    }

    public static final Creator<ContactDAO> CREATOR = new Creator<ContactDAO>() {
        @Override
        public ContactDAO createFromParcel(Parcel in) {
            return new ContactDAO(in);
        }

        @Override
        public ContactDAO[] newArray(int size) {
            return new ContactDAO[size];
        }
    };

    public List<ContactData> getContactData() {
        return contactData;
    }

    public void setContactData(List<ContactData> contactData) {
        this.contactData = contactData;
    }

    private List<ContactData> contactData;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
