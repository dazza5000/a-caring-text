package com.amicly.acaringtext.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by darrankelinske on 1/30/16.
 */
public class Text extends RealmObject {
    @Required
    private String mId;
    @Required
    private String mDateTime;
    @Required
    private String mContact;
    @Required
    private String mContactNumber;
    @Required
    private String mMessage;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        this.mDateTime = dateTime;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        this.mContact = contact;
    }

    public String getContactNumber() {
        return mContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.mContactNumber = contactNumber;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
