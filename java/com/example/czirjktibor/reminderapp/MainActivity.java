package com.example.czirjktibor.reminderapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.czirjktibor.reminderapp.events.UsernameChangedEvent;
import com.example.czirjktibor.reminderapp.fragments.HomeFragment;
import com.example.czirjktibor.reminderapp.fragments.SettingsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private View navigationHeader;
    private TextView navHeaderName;
    private SharedPreferences usernamePrefs;

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationHeader = navigationView.getHeaderView(0);
        navHeaderName = navigationHeader.findViewById(R.id.nav_header_name);

        loadNavHeader();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadNavHeader();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        HomeFragment homeFragment = new HomeFragment();
        changeFragment(homeFragment);
        setToolbarTitle("Alarms");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_alarms: {
                setToolbarTitle("Alarms");
                HomeFragment homeFragment = new HomeFragment();
                changeFragment(homeFragment);
                break;
            }
            case R.id.nav_settings: {
                setToolbarTitle("Settings");
                SettingsFragment settingsFragment = new SettingsFragment();
                changeFragment(settingsFragment);
                break;
            }
        }

        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(UsernameChangedEvent e){
        loadNavHeader();
    }

    private void loadNavHeader() {
        usernamePrefs = PreferenceManager.getDefaultSharedPreferences(this);
        navHeaderName.setText(usernamePrefs.getString("reminderAppUserName", "User name"));
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

}
