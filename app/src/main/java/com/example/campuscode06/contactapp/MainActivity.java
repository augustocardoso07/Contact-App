package com.example.campuscode06.contactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.campuscode06.contactapp.adapters.ContactsAdapter;
import com.example.campuscode06.contactapp.model.Contact;
import com.example.campuscode06.contactapp.provider.ContactModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView contacts;
    FloatingActionButton newContact;
    ArrayList<Contact> contactsList;

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
        refreshList();
    }

    @Override
    public void onClick(View view) {

        startActivityForResult(new Intent(this, NewContactActivity.class), 0);
    }

    private void refreshList() {
        contacts = (ListView) findViewById(R.id.lv_contacts);
        contactsList = new ArrayList<Contact>();

        Cursor contactsCursor = getContentResolver().query(ContactModel.CONTENT_URI, null, null, null, null);
        if (contactsCursor != null) {
            while (contactsCursor.moveToNext()) {
                contactsList.add(new Contact(
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactModel.NAME)),
                        contactsCursor.getString(contactsCursor.getColumnIndex(ContactModel.PHONE))
                ));
            }
        }
        contactsCursor.close();

        ContactsAdapter adapter = new ContactsAdapter(this, contactsList);
        contacts.setAdapter(adapter);
    }

    public void deleteItem(View v) {
        Toast.makeText(this, , Toast.LENGTH_SHORT).show();
    }

}
