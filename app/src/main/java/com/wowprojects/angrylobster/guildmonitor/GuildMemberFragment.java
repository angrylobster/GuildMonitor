package com.wowprojects.angrylobster.guildmonitor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GuildMemberFragment extends Fragment {

    private GuildMember mGuildMember;
    private TextView mGuildMemberTestView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String guildMemberName = (String) getActivity().getIntent()
                                .getSerializableExtra(GuildMemberActivity.EXTRA_GUILD_MEMBER);
        mGuildMember = GuildMemberManager.get(getActivity()).getGuildMember(guildMemberName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guild_member, container, false);

        mGuildMemberTestView = (TextView) view.findViewById(R.id.item_level_view);

        mGuildMemberTestView.setText(mGuildMember.getName());

        return view;
    }
}
