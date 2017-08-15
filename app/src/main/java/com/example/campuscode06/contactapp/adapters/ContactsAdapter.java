package com.example.campuscode06.contactapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.campuscode06.contactapp.R;

import java.util.ArrayList;

/**
 * Created by campuscode06 on 8/14/17.
 */

public class ContactsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList mContactList;

    public ContactsAdapter(Context context, ArrayList contactsList) {
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
        }
        return result;
    }
}
