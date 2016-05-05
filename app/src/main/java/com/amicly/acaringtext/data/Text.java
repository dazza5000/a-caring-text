package com.amicly.acaringtext.data;

import android.support.annotation.NonNull;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by darrankelinske on 1/30/16.
 */
public class Text extends RealmObject {
    @Required
    @PrimaryKey
    private String mId;
    @Required
    private String mDateTime;
    @Required
    private String mContact;
    @Required
    private String mContactNumber;
    @Required
    private String mMessage;

    public Text() {}

    public Text(@NonNull String dateTime, @NonNull String contact, @NonNull String contactNumber, @NonNull String message) {
        mId = UUID.randomUUID().toString();
        mDateTime = dateTime;
        mContact = contact;
        mContactNumber = contactNumber;
        mMessage = message;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String id) {
        mId = id;
    }

    public String getmDateTime() {
        return mDateTime;
    }

    public void setmDateTime(String dateTime) {
        this.mDateTime = dateTime;
    }

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String contact) {
        this.mContact = contact;
    }

    public String getmContactNumber() {
        return mContactNumber;
    }

    public void setmContactNumber(String contactNumber) {
        this.mContactNumber = contactNumber;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String message) {
        this.mMessage = message;
    }

    public boolean isEmpty() {
        return (mDateTime == null || "".equals(mDateTime)) &&
                (mContact == null || "".equals(mContact)) &&
                        (mContactNumber == null || "".equals(mContactNumber)) &&
                                (mMessage == null || "".equals(mMessage));
    }

}
