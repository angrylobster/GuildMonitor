package com.wowprojects.angrylobster.guildmonitor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GuildListActivity extends SingleFragmentActivity {

    public static final String EXTRA_REALM =
            "com.angrylobster.android.guildmonitor.realm";

    public static final String EXTRA_GUILD_NAME =
            "com.angrylobster.android.guildmonitor.guild_name";

    @Override
    protected Fragment createFragment(){
        return new GuildListFragment();
    }

    public static Intent newIntent(Context context, String realm, String guildName){
        Intent intent = new Intent(context, GuildListActivity.class);
        intent.putExtra(EXTRA_REALM, realm);
        intent.putExtra(EXTRA_GUILD_NAME, guildName);
        return intent;
    }
}
