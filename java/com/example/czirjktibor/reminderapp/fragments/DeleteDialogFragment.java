package com.example.czirjktibor.reminderapp.fragments;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.czirjktibor.reminderapp.MainActivity;
import com.example.czirjktibor.reminderapp.R;
import com.example.czirjktibor.reminderapp.database.DatabaseClient;
import com.example.czirjktibor.reminderapp.entitys.Alarm;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteDialogFragment extends DialogFragment {

    private Alarm alarm;

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_delete_dialog, container, false);

        ButterKnife.bind(this, fragment);

        return fragment;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        HomeFragment homeFragment = new HomeFragment();
        ((MainActivity) getContext()).changeFragment(homeFragment);
    }

    @OnClick(R.id.delete_button)
    protected void deleteOnClicked(){
        deleteAlarm(alarm);
    }

    private void deleteAlarm(final Alarm alarm) {
        class DeleteAlarm extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getContext()).getAppDatabase().alarmDao().delete(alarm);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();
                dismiss();
            }
        }

        DeleteAlarm deleteAlarm = new DeleteAlarm();
        deleteAlarm.execute();
    }
}
