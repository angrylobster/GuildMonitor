package com.wowprojects.angrylobster.guildmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GuildMemberFragment extends Fragment {

    private static final String TAG = "GuildMemberFragment";

    private GuildMember mGuildMember;
    private ImageView mGuildMemberProfilePictureView;
    private TextView mGuildMemberNameView;
    private TextView mGuildMemberLevelView;
    private TextView mGuildMemberRaceAndClassView;
    private TextView mItemLevelView;
    private TextView mUldirNormalView;
    private TextView mUldirHeroicView;
    private TextView mUldirMythicView;
    private TextView mRaiderIOView;

    private int mItemLevel = 0;
    private int mNormalRaid = 0;
    private int mHeroicRaid = 0;
    private int mMythicRaid = 0;
    private int mRaiderScore = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        String guildMemberName = (String) getActivity().getIntent()
                                .getSerializableExtra(GuildMemberActivity.EXTRA_GUILD_MEMBER);
        mGuildMember = GuildMemberManager.get(getActivity()).getGuildMember(guildMemberName);
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guild_member, container, false);

        String profilePictureString = "http://render-us.worldofwarcraft.com/character/"
                                        + mGuildMember.getProfilePictureString().replace("avatar", "main");
        String levelString = "Level " + mGuildMember.getLevel();
        String raceAndClass = mGuildMember.getRaceString() + " "
                + mGuildMember.getClassString() + " "
                + mGuildMember.getSpec();

        mGuildMemberNameView = (TextView) view.findViewById(R.id.character_name);
        mGuildMemberLevelView = (TextView) view.findViewById(R.id.character_level);
        mGuildMemberRaceAndClassView = (TextView) view.findViewById(R.id.character_race_and_class);
        mItemLevelView = (TextView) view.findViewById(R.id.character_item_level);
        mUldirNormalView = (TextView) view.findViewById(R.id.raid_uldir_normal);
        mUldirHeroicView = (TextView) view.findViewById(R.id.raid_uldir_heroic);
        mUldirMythicView = (TextView) view.findViewById(R.id.raid_uldir_mythic);
        mRaiderIOView = (TextView) view.findViewById(R.id.raider_io_score);
        mGuildMemberProfilePictureView = (ImageView) view.findViewById(R.id.guild_member_profile_picture);

        mGuildMemberNameView.setText(mGuildMember.getName());
        mGuildMemberLevelView.setText(levelString);
        mGuildMemberRaceAndClassView.setText(raceAndClass);

        Picasso.get().load(profilePictureString)
                .resize(mGuildMemberProfilePictureView.getMaxWidth(),500)
                .centerInside()
                .into(mGuildMemberProfilePictureView);
        return view;
    }


    private class FetchItemsTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject jsonObject = new APIFetcher()
                    .fetchMemberObject("character", "barthilas", mGuildMember.getName());
            parseJSONObject(jsonObject);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mItemLevelView.setText(String.valueOf(mItemLevel));
            mUldirNormalView.setText(String.valueOf(mNormalRaid));
            mUldirHeroicView.setText(String.valueOf(mHeroicRaid));
            mUldirMythicView.setText(String.valueOf(mMythicRaid));
            mRaiderIOView.setText(String.valueOf(mRaiderScore));
        }

        private void parseJSONObject(JSONObject object){
            try {
                JSONObject raidProgressionObject = object.getJSONObject("raid_progression")
                        .getJSONObject("uldir");

                mItemLevel = object.getJSONObject("gear").getInt("item_level_total");
                mNormalRaid = raidProgressionObject.getInt("normal_bosses_killed");
                mHeroicRaid = raidProgressionObject.getInt("heroic_bosses_killed");
                mMythicRaid = raidProgressionObject.getInt("mythic_bosses_killed");
                mRaiderScore = object.getJSONObject("mythic_plus_scores")
                        .getInt("all");
            } catch (JSONException je){
                Log.e(TAG, "Failed to parse JSON object for character", je);
            }
        }
    }

}
