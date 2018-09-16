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

    public String getmClass() {
        return classToString(mClass);
    }

    public void setClass(int aClass) {
        mClass = aClass;
    }

    public String getRace() {
        return raceToString(mRace);
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
        if (mSpec == null){
            return "";
        } else {
            return "(" + mSpec + ")";
        }
    }

    public void setSpec(String spec) {
        mSpec = spec;
    }

    private String raceToString(int race){
        String raceString = "";
        switch (race){
            case 1:
                raceString = "Human";
                break;
            case 2:
                raceString = "Orc";
                break;
            case 3:
                raceString = "Dwarf";
                break;
            case 4:
                raceString = "Night Elf";
                break;
            case 5:
                raceString = "Undead";
                break;
            case 6:
                raceString = "Tauren";
                break;
            case 7:
                raceString = "Gnome";
                break;
            case 8:
                raceString = "Troll";
                break;
            case 9:
                raceString = "Goblin";
                break;
            case 10:
                raceString = "Blood Elf";
                break;
            case 11:
                raceString = "Draenei";
                break;
            case 22:
                raceString = "Worgen";
                break;
            case 24:
            case 25:
            case 26:
                raceString = "Pandaren";
                break;
            case 27:
                raceString = "Nightborne";
                break;
            case 28:
                raceString = "Highmountain Tauren";
                break;
            case 29:
                raceString = "Void Elf";
                break;
            case 30:
                raceString = "Lightforged Draenei";
                break;
            case 34:
                raceString = "Dark Iron Dwarf";
                break;
            case 36:
                raceString = "Mag'har Orc";
                break;
            default:
                raceString = "(null)";
                break;
        }
        return raceString;
    }

    private String classToString(int aClass){
        String classString = "";
        switch (aClass){
            case 1:
                classString = "Warrior";
                break;
            case 2:
                classString = "Paladin";
                break;
            case 3:
                classString = "Hunter";
                break;
            case 4:
                classString = "Rogue";
                break;
            case 5:
                classString = "Priest";
                break;
            case 6:
                classString = "Death Knight";
                break;
            case 7:
                classString = "Shaman";
                break;
            case 8:
                classString = "Mage";
                break;
            case 9:
                classString = "Warlock";
                break;
            case 10:
                classString = "Monk";
                break;
            case 11:
                classString = "Druid";
                break;
            case 12:
                classString = "Demon Hunter";
                break;
            default:
                classString = "(null)";
                break;
        }
        return classString;
    }
}
