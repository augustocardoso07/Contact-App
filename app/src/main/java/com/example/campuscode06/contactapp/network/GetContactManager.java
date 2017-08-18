package com.example.campuscode06.contactapp.network;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.campuscode06.contactapp.model.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetContactManager extends AsyncTask<Void, Integer, Integer>{

    OnSyncListerner listerner;
    String allContacts;


    public GetContactManager(OnSyncListerner osl) {
        listerner = osl;
    }

    public String getContacts() throws IOException {
        OutputStreamWriter writer = null;
        String contentAsString = "";
        try {
            URL url = new URL(ContactManager.CONTACTS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* miliseconds */);
            conn.setConnectTimeout(15000 /* miliseconds */);
            conn.setRequestProperty("Content-length", "0");
            conn.setRequestMethod("GET");
            conn.setAllowUserInteraction(false);
            conn.setUseCaches(false);
            conn.connect();

            StringBuilder builder = new StringBuilder();
            int httpResult = conn.getResponseCode();
            if (httpResult == HttpURLConnection.HTTP_CREATED || httpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                reader.close();
                contentAsString = builder.toString();
            }
            else {
                contentAsString = conn.getResponseMessage();
            }
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }

        return contentAsString;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        int result = -1;
        try {
            allContacts = getContacts();
            result = 0;
        } catch (IOException e) {
            Log.e("IOException",e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer o) {
        super.onPostExecute(o);
        if (o == 0) {
            listerner.onSyncFinish(allContacts);
        } else {

        }
    }
    public interface OnSyncListerner {
        public void onSyncFinish(String contacts);
    }
}

