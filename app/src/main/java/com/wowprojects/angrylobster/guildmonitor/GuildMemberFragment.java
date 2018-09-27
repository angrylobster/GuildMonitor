package com.wowprojects.angrylobster.guildmonitor;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GuildMemberFragment extends Fragment {

    private GuildMember mGuildMember;
    private ImageView mGuildMemberProfilePictureView;
    private TextView mGuildMemberTestView;

    private Drawable mProfilePicture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        String guildMemberName = (String) getActivity().getIntent()
                                .getSerializableExtra(GuildMemberActivity.EXTRA_GUILD_MEMBER);
        mGuildMember = GuildMemberManager.get(getActivity()).getGuildMember(guildMemberName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guild_member, container, false);
        String profilePictureString = "http://render-us.worldofwarcraft.com/character/"
                                        + mGuildMember.getProfilePictureString().replace("avatar", "main");

        mGuildMemberTestView = (TextView) view.findViewById(R.id.item_level_view);
        mGuildMemberProfilePictureView = (ImageView) view.findViewById(R.id.guild_member_profile_picture);

        mGuildMemberTestView.setText(mGuildMember.getName());
        Picasso.get().load(profilePictureString)
                .centerCrop()
                .resize(300, 500)
                .into(mGuildMemberProfilePictureView);
        return view;
    }

    //https://raider.io/api/v1/characters/profile?region=us&realm=barthilas&name=angrylobster&fields=gear%2Craid_progression%2Cmythic_plus_scores

    private class FetchItemsTask extends AsyncTask<Void, Void, List<GuildMember>>{
        @Override
        protected List<GuildMember> doInBackground(Void... voids) {
            List<GuildMember> guildMemberList = new APIFetcher()
                    .fetchGuildMembers("guild", "barthilas", "Last Warning");

            GuildMemberManager.get(getContext()).set(guildMemberList);
            return guildMemberList;
        }

        @Override
        protected void onPostExecute(List<GuildMember> memberList){
            GuildMemberManager.get(getActivity()).sortByName();
//            mGuildMembers = GuildMemberManager.get(getContext()).getGuildMembers();
//            setupAdapter();
        }
    }
}
