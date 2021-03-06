package com.example.campuscode06.contactapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RenanCardoso on 16/08/2017.
 */

public class ContactsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "qcontacts.db";
    private static final int DATABASE_VERSION = 1;

    public ContactsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ContactModel.CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            switch (oldVersion) {
                case 1:
            }
        }
    }
}
