package com.wowprojects.angrylobster.guildmonitor;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GuildListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new GuildListFragment();
    }
}
