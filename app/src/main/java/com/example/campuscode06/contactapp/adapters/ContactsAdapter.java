package com.example.campuscode06.contactapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.campuscode06.contactapp.R;
import com.example.campuscode06.contactapp.model.Contact;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by campuscode06 on 8/14/17.
 */

public class ContactsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Contact> mContactList;

    public ContactsAdapter(Context context, ArrayList<Contact> contactsList) {
        mContext = context;
        mContactList = contactsList;
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int i) {
        return mContactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View result;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            result = inflater.inflate(R.layout.contacts_item_layout, null);
        } else {
            result = view;
            TextView name = (TextView) result.findViewById(R.id.tv_contact_item_name);
            TextView phone = (TextView) result.findViewById(R.id.tv_contact_item_phone);

            name.setText( mContactList.get(i).getName() );
            phone.setText(mContactList.get(i).getPhone());
        }
        return result;
    }
}
