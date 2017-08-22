package com.example.campuscode06.contactapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.campuscode06.contactapp.adapters.ContactsCursorAdapter;
import com.example.campuscode06.contactapp.model.Contact;
import com.example.campuscode06.contactapp.network.GetContactManager;
import com.example.campuscode06.contactapp.provider.ContactModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GetContactManager.OnSyncListerner, SwipeRefreshLayout.OnRefreshListener, ContactsCursorAdapter.OnDeleteItem {

    ListView contacts;
    FloatingActionButton newContact;
    SwipeRefreshLayout refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newContact = (FloatingActionButton) findViewById(R.id.fab_new_contact);
        newContact.setOnClickListener(this);

        refresh = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        refresh.setOnRefreshListener(this);


        //startService(new Intent(this, ContactsServices.class));

        GetContactManager web = new GetContactManager(this);
        web.execute();
    }


    private void refreshList() {
        ContactsCursorAdapter adapter = new ContactsCursorAdapter(this, getContentResolver().query(ContactModel.CONTENT_URI, null, null, null, null) ,true, this);
        contacts = (ListView) findViewById(R.id.lv_contacts);
        contacts.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onClick(View view) {
        startActivityForResult(new Intent(this, NewContactActivity.class), 0);
    }


    @Override
    public void onSyncFinish(String contactsJson) {

        /*Cursor contactsCursor = getContentResolver().query(ContactModel.CONTENT_URI, null, null, null, null);
        if (contactsCursor != null) {
            while (contactsCursor.moveToNext()) {
                contactsList.add(new Contact(
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactModel.NAME)),
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactModel.PHONE)),
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactModel._ID))
                ));
            }
        }
        contactsCursor.close();
        */

        Gson gson = new Gson();
        List<Contact> contactsList = Arrays.asList(gson.fromJson(contactsJson, Contact[].class));

        int deletedRows = getContentResolver().delete(ContactModel.CONTENT_URI, null, null);

        for (Contact c: contactsList) {
            ContentValues contactValue = new ContentValues();
            contactValue.put(ContactModel.NAME, c.getName());
            contactValue.put(ContactModel.PHONE, c.getPhone());

            getContentResolver().insert(ContactModel.CONTENT_URI, contactValue);
        }

        refresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        //onResume();
        GetContactManager web = new GetContactManager(this);
        web.execute();
    }

    @Override
    public void itemDeleted() {
        refreshList();
    }
}
