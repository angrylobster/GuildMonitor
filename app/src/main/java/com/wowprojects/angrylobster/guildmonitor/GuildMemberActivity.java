package com.wowprojects.angrylobster.guildmonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class GuildMemberActivity extends SingleFragmentActivity{

    public static final String EXTRA_GUILD_MEMBER =
            "com.angrylobster.android.guildmonitor.guild_member_id";

    public static Intent newIntent(Context context, String guildMemberName){
        Intent intent = new Intent(context, GuildMemberActivity.class);
        intent.putExtra(EXTRA_GUILD_MEMBER, guildMemberName);
        return intent;
    }

    protected Fragment createFragment(){
        return new GuildMemberFragment();
    }
}
