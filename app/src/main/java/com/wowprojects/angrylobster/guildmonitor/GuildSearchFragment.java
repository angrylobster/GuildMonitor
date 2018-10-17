package com.wowprojects.angrylobster.guildmonitor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GuildSearchFragment extends Fragment {

    private Spinner mSpinner;
    private EditText mGuildSearchEditText;
    private Button mSearchButton;
    private String mSelectedRealm = null;

    final static String TAG="GuildSearchFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guild_search, container, false);

        mSpinner = (Spinner) view.findViewById(R.id.realm_spinner);
        mGuildSearchEditText = (EditText) view.findViewById(R.id.guild_edit_text);
        mSearchButton = (Button) view.findViewById(R.id.search_button);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.realm_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    mSelectedRealm = null;
                } else {
                    mSelectedRealm = adapterView.getItemAtPosition(i).toString();
                }
                Toast.makeText(getContext(), mSelectedRealm + " is selected",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guildSearched= mGuildSearchEditText.getText().toString();
                if (mSelectedRealm == null || guildSearched.isEmpty()){
                    Toast.makeText(getContext(), "Try again!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = GuildListActivity.newIntent(getActivity(), mSelectedRealm,
                            guildSearched);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
