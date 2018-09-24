package com.wowprojects.angrylobster.guildmonitor;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIFetcher {

    private static final String TAG = "APIFetcher";
    private static final String API_KEY = "j2qnaqav2wvb2kxmwtf9jbh9t83cfx3r";

    public List<GuildMember> fetchGuildMembers(String type, String realm, String target){
        List<GuildMember> guildMemberList = new ArrayList<>();
        try {
            JSONObject jsonObject = getJSONObject(type, realm, target);
            parseItems(guildMemberList, jsonObject);
        } catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return guildMemberList;
    }

    private byte[] getURLBytes(String URLSpec) throws IOException{
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

    private JSONObject getJSONObject(String type, String realm, String target)
            throws IOException, JSONException{

        String url = buildURLString(type, realm, target);
        String JSONString = new String(getURLBytes(buildURLString(type, realm, target)));
        JSONObject jsonObject = new JSONObject(JSONString);

        return jsonObject;
    }

    private String buildURLString(String type, String realm, String target){

        Uri.Builder uriBuilder = new Uri.Builder();

        uriBuilder.scheme("https")
                .authority("us.api.battle.net")
                .appendPath("wow")
                .appendPath(type)
                .appendPath(realm)
                .appendPath(target);

        switch(type) {
            case "guild":
                uriBuilder.appendQueryParameter("fields", "members");
                break;
            case "character":
                uriBuilder.appendQueryParameter("fields", "items");
                break;
        }

        uriBuilder.appendQueryParameter("locale", "en_US")
                .appendQueryParameter("apikey", API_KEY);

        return uriBuilder.build().toString();
    }

    private void parseItems(List<GuildMember> memberList, JSONObject JSONGuildMembersBody)
            throws JSONException{

        JSONArray JSONMembersArray = JSONGuildMembersBody.getJSONArray("members");

        for (int i = 0; i < JSONMembersArray.length(); i++){
            JSONObject JSONMemberObject = JSONMembersArray.getJSONObject(i).getJSONObject("character");

            GuildMember member = new GuildMember();
            
            if (JSONMemberObject.has("spec")){
                member.setSpec(JSONMemberObject.getJSONObject("spec").getString("name"));
            }

            member.setName(JSONMemberObject.getString("name"));
            member.setClassValue(JSONMemberObject.getInt("class"));
            member.setGender(JSONMemberObject.getInt("gender"));
            member.setLevel(JSONMemberObject.getInt("level"));
            member.setRace(JSONMemberObject.getInt("race"));

            memberList.add(member);
        }
    }
}
