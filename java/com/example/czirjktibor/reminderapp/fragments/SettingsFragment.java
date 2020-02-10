package com.example.czirjktibor.reminderapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.czirjktibor.reminderapp.R;
import com.example.czirjktibor.reminderapp.events.UsernameChangedEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    @BindView(R.id.settings_username)
    EditText settings_username;
    @BindView(R.id.username_save)
    Button username_save;

    private SharedPreferences usernamePref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_settings, container, false);

        usernamePref = PreferenceManager.getDefaultSharedPreferences(getContext());

        ButterKnife.bind(this, fragment);

        settings_username.setText(usernamePref.getString("reminderAppUserName", ""));

        settings_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                usernamePref.edit().putString("reminderAppUserName", s.toString()).commit();
                EventBus.getDefault().post(new UsernameChangedEvent());
            }
        });

        return fragment;
    }
}