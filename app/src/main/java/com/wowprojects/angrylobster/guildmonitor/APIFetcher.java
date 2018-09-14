package com.wowprojects.angrylobster.guildmonitor;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIFetcher {

    private static final String TAG = "APIFetcher";
    private static final String API_KEY = "j2qnaqav2wvb2kxmwtf9jbh9t83cfx3r";

    public byte[] getURLBytes(String URLSpec) throws IOException{
        URL url = new URL(URLSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() +
                    ": with " +
                    URLSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getURLString(String URLSpec) throws IOException{
        return new String(getURLBytes(URLSpec));
    }

    public void fetchItems(){
        try {
            String url = Uri.parse("https://us.api.battle.net/wow/guild/barthilas/Last%20Warning")
                    .buildUpon()
                    .appendQueryParameter("fields", "members")
                    .appendQueryParameter("locale", "en_US")
                    .appendQueryParameter("apikey", API_KEY)
                    .build().toString();

            String JSONString = getURLString(url);
            Log.i(TAG, "Received JSON: " + JSONString);
            Log.i(TAG, "JSONString length is " + JSONString.length());
            JSONObject JSONGuildMembers = new JSONObject(JSONString);
        } catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
    }
}
