package com.example.campuscode06.contactapp.model;

/**
 * Created by RenanCardoso on 16/08/2017.
 */

public class Contact {
    private String mName;
    private String mPhone;
    private String mId;

    public Contact(String name, String phone, String Id) {
        mName = name;
        mPhone = phone;
        mId = Id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getId() {
        return mId;
    }

    public void setId(String Id) {
        mId = Id;
    }

    @Override
    public String toString() {
        return this.mName + " - " + this.mPhone;
    }
}
