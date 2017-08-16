package com.example.campuscode06.contactapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campuscode06.contactapp.provider.ContactModel;

public class NewContactActivity extends AppCompatActivity implements View.OnClickListener {

    Button insertContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        insertContact = (Button) findViewById(R.id.bt_insert_contact);

        insertContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText name = (EditText) findViewById(R.id.et_name);
        EditText phone = (EditText) findViewById(R.id.et_phone);

        ContentValues contactValue = new ContentValues();

        contactValue.put(ContactModel.NAME, name.getText().toString());
        contactValue.put(ContactModel.PHONE, phone.getText().toString());

        Uri result =getContentResolver().insert(ContactModel.CONTENT_URI, contactValue);
        if (result != null) {
            Toast.makeText(this, "Contato Adicionado com id: " + result.getLastPathSegment(), Toast.LENGTH_SHORT).show();
            finish();
        }

        //startActivity(new Intent(this, MainActivity.class));
    }
}
