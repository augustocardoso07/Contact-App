package com.example.campuscode06.contactapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campuscode06.contactapp.R;
import com.example.campuscode06.contactapp.model.Contact;
import com.example.campuscode06.contactapp.provider.ContactModel;

import org.w3c.dom.Text;

import java.util.ArrayList;


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
        View result = view;

        if (result == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            result = inflater.inflate(R.layout.contacts_item_layout, null);
        }

        TextView name = result.findViewById(R.id.tv_contact_item_name);
        TextView phone = result.findViewById(R.id.tv_contact_item_phone);

        name.setText( mContactList.get(i).getName() );
        phone.setText(mContactList.get(i).getPhone());

        ImageButton deleteButton = result.findViewById(R.id.bt_delete_item);
        final int ii = i;

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.withAppendedPath(ContactModel.CONTENT_URI, mContactList.get(ii).getId());
                int count = mContext.getContentResolver().delete(uri, "", new String[] {});
                if (count == 1) {
                    Toast.makeText(mContext, "Contato deletado com sucesso", Toast.LENGTH_SHORT).show();
                    mContactList.remove(ii);
                    notifyDataSetChanged();
                }

            }
        });


        return result;
    }


}
