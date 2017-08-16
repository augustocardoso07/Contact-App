package com.example.campuscode06.contactapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.campuscode06.contactapp.adapters.ContactsAdapter;
import com.example.campuscode06.contactapp.models.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView contacts;
    FloatingActionButton newContact;
    ArrayList<Contact> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = (ListView) findViewById(R.id.lv_contacts);

        contactsList = new ArrayList<Contact>();

        contactsList.add(new Contact("Renan", "1234567"));
        contactsList.add(new Contact("Augusto", "1234567"));
        contactsList.add(new Contact("Cardoso", "1234567"));
        contactsList.add(new Contact("oi", "1234567"));

        ContactsAdapter adapter = new ContactsAdapter(this, contactsList);

        contacts.setAdapter(adapter);

        newContact = (FloatingActionButton) findViewById(R.id.fab_new_contact);
        newContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        startActivityForResult(new Intent(this, NewContactActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle result = data.getBundleExtra("data");
        contactsList.add(new Contact(result.getString("name"), result.getString("phone")));
    }
}
