package com.example.campuscode06.contactapp;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campuscode06.contactapp.provider.ContactModel;
import com.example.campuscode06.contactapp.network.PostContactManager;

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

        String contactName = name.getText().toString();

        String contactPhone = phone.getText().toString();

        boolean err = false;



        if (contactName.equals("")) {
            Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            name.setError("Nome nao pode ficar em branco");
            err = true;
        }

        if (contactPhone.equals("")) {
            phone.setError("Telefone nao pode ficar em branco");
            err = true;
        }

        if (err) {
            return;
        }

        contactValue.put(ContactModel.NAME, contactName);
        contactValue.put(ContactModel.PHONE, contactPhone);

        Uri result =getContentResolver().insert(ContactModel.CONTENT_URI, contactValue);
        if (result != null) {
            Toast.makeText(this, "Contato Adicionado com id: " + result.getLastPathSegment(), Toast.LENGTH_SHORT).show();
            PostContactManager postContactManager = new PostContactManager(this, contactName, contactPhone);
            postContactManager.execute();
            finish();
        }

        //startActivity(new Intent(this, MainActivity.class));
    }
}
