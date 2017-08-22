package com.example.campuscode06.contactapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscode06.contactapp.R;
import com.example.campuscode06.contactapp.provider.ContactModel;

/**
 * Created by campuscode06 on 8/21/17.
 */

public class ContactsCursorAdapter extends CursorAdapter {


    public interface OnDeleteItem {
        void itemDeleted();
    }

    OnDeleteItem odi;
    
    public ContactsCursorAdapter(Context context, Cursor c, boolean autoRequery, OnDeleteItem odi) {
        super(context, c, autoRequery);
        this.odi = odi;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return inflater.inflate(R.layout.contacts_item_layout, viewGroup, false);

    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView name = view.findViewById(R.id.tv_contact_item_name);
        TextView phone = view.findViewById(R.id.tv_contact_item_phone);

        name.setText(cursor.getString(cursor.getColumnIndex(ContactModel.NAME)));
        phone.setText(cursor.getString(cursor.getColumnIndex(ContactModel.PHONE)));

        ImageButton deleteItem = view.findViewById(R.id.bt_delete_item);

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = cursor.getString(cursor.getColumnIndex(ContactModel._ID));
                Uri uri = Uri.withAppendedPath(ContactModel.CONTENT_URI, id);
                int result = context.getContentResolver().delete(uri, null, null);
                if (result == 1) {
                    Toast.makeText(context, "Item deletado com sucesso: " + id , Toast.LENGTH_SHORT).show();

                    odi.itemDeleted();
                }
            }
        });

    }


}
