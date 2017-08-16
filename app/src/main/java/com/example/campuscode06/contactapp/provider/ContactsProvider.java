package com.example.campuscode06.contactapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

public class ContactsProvider extends ContentProvider{
    private ContactsDatabaseHelper mDBHelper;
    private static final int URI_CONTACT = 1;
    private static final int URI_CONTACT_ID = 2;

    private static final UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Contact.AUTHORITY, "contact", URI_CONTACT);
        sUriMatcher.addURI(Contact.AUTHORITY, "contact/#", URI_CONTACT_ID);
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new ContactsDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case URI_CONTACT:
                return ContactModel.CONTENT_TYPE;

            case URI_CONTACT_ID:
                return ContactModel.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (sUriMatcher.match(uri)) {
            case URI_CONTACT:
                return insertContact(contentValues);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    private Uri insertContact(ContentValues values) {
        Uri returnUri = null;
        try {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            long id = db.insert(ContactModel.TABLE_NAME, null, values);

            if (id > 0) {
                returnUri = ContentUris.withAppendedId(ContactModel.CONTENT_URI, id);
                getContext().getContentResolver().notifyChange(returnUri, null);
            }
        }
        catch (SQLiteException e) {
            Log.e("Contatos", "Could not write to DB: " + e.getMessage());
        }

        return returnUri;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case URI_CONTACT:
                qb.setTables(ContactModel.TABLE_NAME);
                break;
            case URI_CONTACT_ID:
                qb.setTables(ContactModel.TABLE_NAME);
                qb.appendWhere(ContactModel._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown or not supported URI " + uri);
        }

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        int count;
        String id;

        switch (sUriMatcher.match(uri)) {
            case URI_CONTACT_ID:
                id = uri.getLastPathSegment();
                count = db.delete(ContactModel.TABLE_NAME, ContactModel._ID + "=" + id + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""), whereArgs);

                break;
            case URI_CONTACT:
                count = db.delete(ContactModel.TABLE_NAME, null, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown or not supported URI: " + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String where, String[] whereArgs) {
        int count = 0;
        try {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            String id = null;

            switch (sUriMatcher.match(uri)) {
                case URI_CONTACT_ID:
                    id = uri.getLastPathSegment();
                    count = db.update(ContactModel.TABLE_NAME, contentValues, ContactModel._ID + "=" + id + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""), whereArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown or not supported URI: " + uri);
            }
        }
        catch (SQLiteException e) {
            Log.e("Contatos", "Could not write to DB: " + e.getMessage());
        }
        return count;
    }
}
