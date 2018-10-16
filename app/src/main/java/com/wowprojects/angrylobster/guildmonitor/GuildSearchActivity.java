package com.wowprojects.angrylobster.guildmonitor;

import android.support.v4.app.Fragment;

public class GuildSearchActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new GuildSearchFragment();
    }
}
