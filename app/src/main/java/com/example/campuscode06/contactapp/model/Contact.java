package com.example.campuscode06.contactapp.model;

/**
 * Created by RenanCardoso on 16/08/2017.
 */

public class Contact {
    private String mName;
    private String mPhone;

    public Contact(String name, String phone) {
        mName = name;
        mPhone = phone;
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

    @Override
    public String toString() {
        return this.mName + " - " + this.mPhone;
    }
}
