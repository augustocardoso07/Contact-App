package com.example.campuscode06.contactapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by campuscode06 on 8/15/17.
 */

public class ContactsProvider extends ContentProvider {

    private ContactsDatabaseHelper mDbHelper;

    private static final int URI_CONTACT = 1;
    private static final int URI_CONTACT_ITEM = 2;

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Contacts.AUTORITHY, ContactModel.CONTENT_TYPE, URI_CONTACT);
        sUriMatcher.addURI(Contacts.AUTORITHY, ContactModel.CONTENT_ITEM_TYPE, URI_CONTACT_ITEM);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new ContactsDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projeciton, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case URI_CONTACT:
                qb.setTables(ContactModel.TABLE_NAME);
                break;
            case URI_CONTACT_ITEM:
                qb.setTables(ContactModel.TABLE_NAME);
                qb.appendWhere(ContactModel._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknow URI" + uri);
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor c = qb.query(db, projeciton, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (sUriMatcher.match(uri)) {
            case (URI_CONTACT):
                return insertContact(contentValues);
            default:
                throw new IllegalArgumentException("Unkhow URI" + uri);
        }
    }

    private Uri insertContact(ContentValues values) {
        Uri returnUri = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(ContactModel.TABLE_NAME, null, values);

        if (id > 0) {
            returnUri = ContentUris.withAppendedId(ContactModel.CONTENT_URI, id);

        }
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
