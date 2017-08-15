package com.example.campuscode06.contactapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.campuscode06.contactapp.adapters.ContactsAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView contacts;
    FloatingActionButton newContact;
    ArrayList contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = (ListView) findViewById(R.id.lv_contacts);

        contactsList = new ArrayList();

        contactsList.add("Renan");
        contactsList.add("Renata");
        contactsList.add("Rafael");
        contactsList.add("Rodrigo");

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
        contactsList.add(result.getString("name"));
    }
}
