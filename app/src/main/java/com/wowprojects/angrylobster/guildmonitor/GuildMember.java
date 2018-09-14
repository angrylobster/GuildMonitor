package com.wowprojects.angrylobster.guildmonitor;

public class GuildMember {
    private String mName;
    private int mClass;
    private int mRace;
    private int mGender;
    private int mLevel;
    private String mSpec;

    public GuildMember(){
        mName = null;
        mClass = -1;
        mRace = -1;
        mGender = -1;
        mLevel = -1;
        mSpec = null;
    }

    public GuildMember(String name, int aClass, int race, int gender, int level, String spec) {
        mName = name;
        mClass = aClass;
        mRace = race;
        mGender = gender;
        mLevel = level;
        mSpec = spec;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getmClass() {
        return mClass;
    }

    public void setClass(int aClass) {
        mClass = aClass;
    }

    public int getRace() {
        return mRace;
    }

    public void setRace(int race) {
        mRace = race;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public String getSpec() {
        return mSpec;
    }

    public void setSpec(String spec) {
        mSpec = spec;
    }
}
