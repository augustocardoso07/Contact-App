package com.example.campuscode06.contactapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        Intent intent = new Intent();
        Bundle data = new Bundle();

        EditText name = (EditText) findViewById(R.id.et_name);
        EditText phone = (EditText) findViewById(R.id.et_phone);

        data.putString("name", name.getText().toString());
        data.putString("phone", phone.getText().toString());
        intent.putExtra("data", data);
        setResult(0, intent);
        finish();

        //startActivity(new Intent(this, MainActivity.class));
    }
}
