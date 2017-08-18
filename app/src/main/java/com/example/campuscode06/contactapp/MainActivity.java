package com.example.campuscode06.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.campuscode06.contactapp.adapters.ContactsAdapter;
import com.example.campuscode06.contactapp.model.Contact;
import com.example.campuscode06.contactapp.network.GetContactManager;
import com.example.campuscode06.contactapp.network.PostContactManager;
import com.example.campuscode06.contactapp.provider.ContactModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GetContactManager.OnSyncListerner {

    ListView contacts;
    FloatingActionButton newContact;
    List<Contact> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newContact = (FloatingActionButton) findViewById(R.id.fab_new_contact);
        newContact.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GetContactManager web = new GetContactManager(this);
        web.execute();
    }

    @Override
    public void onClick(View view) {

        startActivityForResult(new Intent(this, NewContactActivity.class), 0);
    }


    @Override
    public void onSyncFinish(String contactsJson) {

        contacts = (ListView) findViewById(R.id.lv_contacts);
        contactsList = new ArrayList<Contact>();

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
        contactsList = Arrays.asList(gson.fromJson(contactsJson, Contact[].class));

        ContactsAdapter adapter = new ContactsAdapter(this, contactsList);
        contacts.setAdapter(adapter);
    }
}
