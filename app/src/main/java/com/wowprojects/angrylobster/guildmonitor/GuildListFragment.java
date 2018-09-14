package com.wowprojects.angrylobster.guildmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class GuildListFragment extends Fragment {

    private static final String TAG = "GuildListFragment";

    private RecyclerView mRecyclerView;
    private GuildAdapter mGuildAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guild_list, container, false);

        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.guild_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        GuildMemberManager guildMemberManager = GuildMemberManager.get(getActivity());
        List<GuildMember> guildMembers = guildMemberManager.getGuildMembers();

        mGuildAdapter = new GuildAdapter(guildMembers);
        mRecyclerView.setAdapter(mGuildAdapter);
    }

    private class GuildHolder extends RecyclerView.ViewHolder{

        private TextView mGuildMemberNameView;
        private TextView mGuildMemberLevelView;

        private GuildMember mGuildMember;

        public GuildHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_guild_member_view, parent, false));

            mGuildMemberNameView = (TextView) itemView.findViewById(R.id.guild_member_name);
            mGuildMemberLevelView = (TextView) itemView.findViewById(R.id.guild_member_class);
        }

        public void bind(GuildMember member){
            mGuildMember = member;

            mGuildMemberLevelView.setText(member.getmClass());
            mGuildMemberNameView.setText(member.getName());
        }
    }

    private class GuildAdapter extends RecyclerView.Adapter<GuildHolder>{
        private List<GuildMember> mGuildMembers;

        public GuildAdapter(List<GuildMember> guildMembers){
            mGuildMembers = guildMembers;
        }

        @Override
        public GuildHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new GuildHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(GuildHolder holder, int position) {
            GuildMember member = mGuildMembers.get(position);
            holder.bind(member);
        }

        @Override
        public int getItemCount() {
            return mGuildMembers.size();
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            new APIFetcher().fetchItems();
            return null;
        }
    }
}
