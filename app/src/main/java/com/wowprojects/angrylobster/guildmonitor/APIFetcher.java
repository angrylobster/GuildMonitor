package com.wowprojects.angrylobster.guildmonitor;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
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

        switch(type) {
            case "guild":
                uriBuilder.scheme("https")
                        .authority("us.api.battle.net")
                        .appendPath("wow")
                        .appendPath(type)
                        .appendPath(realm)
                        .appendPath(target)
                        .appendQueryParameter("fields", "members")
                        .appendQueryParameter("locale", "en_US")
                        .appendQueryParameter("apikey", API_KEY);
                break;
            case "character":
                uriBuilder.scheme("https")
                        .authority("raider.io")
                        .appendPath("api")
                        .appendPath("v1")
                        .appendPath("characters")
                        .appendPath("profile")
                        .appendQueryParameter("region", "us")
                        .appendQueryParameter("realm", realm)
                        .appendQueryParameter("name", target)
                        .appendQueryParameter("fields", "gear")
                        .appendEncodedPath(",raid_progression,mythic_plus_scores");
                break;
        }
        return uriBuilder.build().toString();
    }

    private void parseItems(List<GuildMember> memberList, JSONObject JSONGuildMembersBody)
            throws JSONException{

        JSONArray JSONMembersArray = JSONGuildMembersBody.getJSONArray("members");

        for (int i = 0; i < JSONMembersArray.length(); i++){
            JSONObject JSONMemberObject = JSONMembersArray.getJSONObject(i).getJSONObject("character");
            int rank = JSONMembersArray.getJSONObject(i).getInt("rank");
            String spec = "";

            if(JSONMemberObject.has("spec")){
                spec = JSONMemberObject.getJSONObject("spec").getString("name");
            }

            GuildMember member = new GuildMember(
                    JSONMemberObject.getString("name"),
                    JSONMemberObject.getInt("class"),
                    JSONMemberObject.getInt("race"),
                    JSONMemberObject.getInt("gender"),
                    JSONMemberObject.getInt("level"),
                    spec,
                    rank,
                    JSONMemberObject.getString("thumbnail"));

            memberList.add(member);
        }
    }
}
