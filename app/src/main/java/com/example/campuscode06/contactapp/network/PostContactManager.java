package com.example.campuscode06.contactapp.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by campuscode06 on 8/16/17.
 */

public class PostContactManager extends AsyncTask<Void, Integer, Integer> {

    String mName;
    String mPhone;
    Context mContext;

    public PostContactManager(Context context, String name, String phone) {
        mName = name;
        mPhone = phone;
        mContext = context;
    }

    private String postContact(String name, String phone) throws IOException {
        OutputStreamWriter writer = null;
        String contentAsString = "";
        try {
            URL url = new URL(ContactManager.CONTACTS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* miliseconds */);
            conn.setConnectTimeout(15000 /* miliseconds */);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject data = new JSONObject();
            data.put("name", name);
            data.put("phone", phone);
            JSONObject contact = new JSONObject();
            contact.put("contact", data);

            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(contact.toString());
            writer.flush();

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
        catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }
        return contentAsString;
    }

    @Override
    protected Integer doInBackground(Void[] objects) {

        int result = -1;
        try {
            postContact(mName, mPhone);
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
            Toast.makeText(mContext, "Dados salvos na internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Sem acesso a internet", Toast.LENGTH_SHORT).show();
        }
    }
}
