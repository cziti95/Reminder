package com.example.czirjktibor.reminderapp.fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.czirjktibor.reminderapp.MainActivity;
import com.example.czirjktibor.reminderapp.R;
import com.example.czirjktibor.reminderapp.adapters.RecyclerViewAdapter;
import com.example.czirjktibor.reminderapp.database.DatabaseClient;
import com.example.czirjktibor.reminderapp.entitys.Alarm;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    @BindView(R.id.home_recycler_view)
    RecyclerView recyclerView;

    private SharedPreferences usernamePrefs;
    private RecyclerViewAdapter adapter;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this,fragment);

        getAlarms();

        return fragment;
    }

    @OnClick(R.id.fab)
    public void floatingButtonClick(){
        usernamePrefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        if(usernamePrefs.getString("reminderAppUserName", null) == null){
            Toast.makeText(getContext(), "You must set username first at settings!", Toast.LENGTH_LONG).show();
        } else {
            EditorFragment editorFragment = new EditorFragment();
            ((MainActivity) getActivity()).changeFragment(editorFragment);
        }
    }

    private void getAlarms(){
        class GetAlarms extends AsyncTask<Void, Void, List<Alarm>>{

            @Override
            protected List<Alarm> doInBackground(Void... voids) {
                List<Alarm> alarmList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .alarmDao()
                        .getAll();
                return alarmList;
            }

            @Override
            protected void onPostExecute(List<Alarm> alarms) {
                super.onPostExecute(alarms);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new RecyclerViewAdapter(getContext(), alarms);
                recyclerView.setAdapter(adapter);
            }
        }
        GetAlarms getAlarms = new GetAlarms();
        getAlarms.execute();
    }

}
