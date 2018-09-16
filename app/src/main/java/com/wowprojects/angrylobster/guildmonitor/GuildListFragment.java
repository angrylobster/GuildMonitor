package com.wowprojects.angrylobster.guildmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GuildListFragment extends Fragment {

    private static final String TAG = "GuildListFragment";

    private List<GuildMember> mGuildMembers = new ArrayList<>();
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

        setupAdapter();

        return view;
    }

    private void setupAdapter(){
        if (isAdded()){
            mRecyclerView.setAdapter(new GuildAdapter(mGuildMembers));
        }
    }

    private class GuildHolder extends RecyclerView.ViewHolder{

        private TextView mGuildMemberNameView;
        private TextView mGuildMemberDetailsView;

        private GuildMember mGuildMember;

        public GuildHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_guild_member_view, parent, false));

            mGuildMemberNameView = (TextView) itemView.findViewById(R.id.guild_member_name);
            mGuildMemberDetailsView = (TextView) itemView.findViewById(R.id.guild_member_details);
        }

        public void bind(GuildMember member){
            mGuildMember = member;

            String detailsString = "Level " + member.getLevel()
                                    + " " + member.getRace()
                                    + " " + member.getmClass()
                                    + " " + member.getSpec();

            mGuildMemberNameView.setText(member.getName());
            mGuildMemberDetailsView.setText(detailsString);
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

    private class FetchItemsTask extends AsyncTask<Void, Void, List<GuildMember>>{

        @Override
        protected List<GuildMember> doInBackground(Void... voids) {
            return new APIFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<GuildMember> memberList){
            GuildMemberManager.get(getContext()).set(memberList);
            mGuildMembers = GuildMemberManager.get(getContext()).getGuildMembers();
            setupAdapter();
        }
    }
}
