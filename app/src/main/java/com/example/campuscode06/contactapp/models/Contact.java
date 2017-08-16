package com.example.campuscode06.contactapp.models;

/**
 * Created by campuscode06 on 8/15/17.
 */

public class Contact {
    String mName;
    String mPhone;
    public Contact(String name, String phone) {
        mName = name;
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }
}
