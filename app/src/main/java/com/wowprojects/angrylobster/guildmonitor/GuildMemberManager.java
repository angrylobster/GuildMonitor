package com.wowprojects.angrylobster.guildmonitor;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GuildMemberManager {

    private static GuildMemberManager sGuildMemberManager;

    private List<GuildMember> mGuildMembers;

    public static GuildMemberManager get(Context context){
        if (sGuildMemberManager == null){
            sGuildMemberManager = new GuildMemberManager(context);

        }
        return sGuildMemberManager;
    }

    private GuildMemberManager(Context context){
        mGuildMembers = new ArrayList<GuildMember>();

    }

    public GuildMember getGuildMember(String name){
        for (GuildMember guildMember : mGuildMembers){
            if (guildMember.getName().equals(name)){
                return guildMember;
            }
        }
        return null;
    }

    public List<GuildMember> getGuildMembers(){
        return mGuildMembers;
    }

    public void set(List<GuildMember> guildMembers){
        mGuildMembers = guildMembers;
    }

    public void sortByName(){
        Collections.sort(mGuildMembers, new GuildListSorter());
    }

    private class GuildListSorter implements Comparator<GuildMember>{
        @Override
        public int compare(GuildMember memberOne, GuildMember memberTwo) {
            if(memberOne.getLevel() > memberTwo.getLevel()){
                return -1;
            }

            if(memberOne.getLevel() < memberTwo.getLevel()){
                return 1;
            }
            
            if (memberOne.getRank() < memberTwo.getRank()){
                return -1;
            }

            if (memberOne.getRank() > memberTwo.getRank()){
                return 1;
            }
            
            return memberOne.getName().compareTo(memberTwo.getName());
        }
    }
}
