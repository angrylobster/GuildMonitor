package com.wowprojects.angrylobster.guildmonitor;

public class GuildMember {
    private String mName;
    private String mClass;
    private String mRace;
    private int mLevel;

    public GuildMember(String name, String aClass, String race, int level) {
        mName = name;
        mClass = aClass;
        mRace = race;
        mLevel = level;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getmClass() {
        return mClass;
    }

    public void setClass(String aClass) {
        mClass = aClass;
    }

    public String getRace() {
        return mRace;
    }

    public void setRace(String race) {
        mRace = race;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }
}
