package com.example.czirjktibor.reminderapp.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.czirjktibor.reminderapp.MainActivity;
import com.example.czirjktibor.reminderapp.R;
import com.example.czirjktibor.reminderapp.database.DatabaseClient;
import com.example.czirjktibor.reminderapp.entitys.Alarm;
import com.example.czirjktibor.reminderapp.entitys.Color;
import com.example.czirjktibor.reminderapp.notifications.NotificationPublisher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.czirjktibor.reminderapp.notifications.ReminderApp.CHANNEL_ID;

public class EditorFragment extends Fragment {

    private static final int TARGET_FRAGMENT_REQUEST_CODE = 1;
    public static final String TAG = EditorFragment.class.getSimpleName();
    private NotificationManagerCompat notificationManagerCompat;

    @BindView(R.id.color_selector)
    Button colorSelectorButton;
    @BindView(R.id.hour_spinner)
    Spinner hourSpinner;
    @BindView(R.id.minute_spinner)
    Spinner minuteSpinner;
    @BindView(R.id.second_spinner)
    Spinner secondSpinner;
    @BindView(R.id.alarm_name)
    EditText alarmName;

    private Alarm alarm;
    private Calendar calendar;
    private boolean okFlag = false;

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_editor, container, false);

        ButterKnife.bind(this, fragment);

        populateSpinner(24,1,hourSpinner);
        populateSpinner(60,5,minuteSpinner);
        populateSpinner(60,1,secondSpinner);

        if (alarm == null) {
            ((MainActivity) getContext()).setToolbarTitle("Add new alarm");
            alarm = new Alarm();
        } else {
            ((MainActivity) getContext()).setToolbarTitle("Edit alarm");
            loadAlarm();
            Log.i(TAG, "Alarm betöltve");
        }

        notificationManagerCompat = NotificationManagerCompat.from(getContext());

        return fragment;
    }

    private void loadAlarm(){
        alarmName.setText(alarm.getAlarmName());
        setCircleColor(alarm.getColor());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(alarm.getAlarmTime());

        hourSpinner.setSelection(spinnerPosition(hourSpinner, calendar.get(Calendar.HOUR_OF_DAY)));
        minuteSpinner.setSelection(spinnerPosition(minuteSpinner, calendar.get(Calendar.MINUTE)));
        secondSpinner.setSelection(spinnerPosition(secondSpinner, calendar.get(Calendar.SECOND)));
    }

    private int spinnerPosition(Spinner spinner, int value){
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        return adapter.getPosition(value);
    }

    private void populateSpinner(int maxValue, int interval, Spinner spinner){
        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < maxValue; i+=interval){
            values.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.color_selector)
    public void onDialogShowFragment(){
        ColorSelectorFragment colorSelectorFragment = new ColorSelectorFragment();
        FragmentManager fragmentManager = getFragmentManager();

        colorSelectorFragment.setTargetFragment(this,TARGET_FRAGMENT_REQUEST_CODE);
        colorSelectorFragment.show(fragmentManager, "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == TARGET_FRAGMENT_REQUEST_CODE){
            Color result = (Color) data.getSerializableExtra("Color");
            setCircleColor(result);
            alarm.setColor(result);
        }
    }

    public static Intent newIntent(Color color){
        Intent intent = new Intent();
        intent.putExtra("Color", color);
        return intent;
    }

    private void setCircleColor(Color color){
        switch (color){
            case BLUE:
                colorSelectorButton.setBackgroundTintList(getContext().
                        getResources().getColorStateList(R.color.colorBlue));
                break;
            case RED:
                colorSelectorButton.setBackgroundTintList(getContext().
                        getResources().getColorStateList(R.color.colorRed));
                break;
            case YELLOW:
                colorSelectorButton.setBackgroundTintList(getContext().
                        getResources().getColorStateList(R.color.colorYellow));
                break;
            case ORANGE:
                colorSelectorButton.setBackgroundTintList(getContext().
                        getResources().getColorStateList(R.color.colorOrange));
                break;
            case GREEN:
                colorSelectorButton.setBackgroundTintList(getContext().
                        getResources().getColorStateList(R.color.colorGreen));
                break;
            case CYAN:
                colorSelectorButton.setBackgroundTintList(getContext().
                        getResources().getColorStateList(R.color.colorCyan));
                break;
        }
    }

    private void getAlarmPieces(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        String username = prefs.getString("reminderAppUserName", "");
        String name = alarmName.getText().toString();
        Date date = createDateBySpinners((int) hourSpinner.getSelectedItem(),
                (int) minuteSpinner.getSelectedItem(),
                (int) secondSpinner.getSelectedItem());

        alarm.setUserName(username);
        alarm.setAlarmName(name);
        alarm.setAlarmTime(date);
    }

    private Date createDateBySpinners(int hour, int min, int sec){
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(System.currentTimeMillis());

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, currentDate.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, currentDate.get(Calendar.DATE));

        if (currentDate.get(Calendar.HOUR_OF_DAY) > hour){
            calendar.add(Calendar.DATE, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);

        return  calendar.getTime();
    }

    @OnClick(R.id.alarm_save)
    public void onAlarmSaveButtonClicked(){
        getAlarmPieces();

        saveTask();
    }

    private void saveTask(){
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                DatabaseClient.getInstance(getContext()).getAppDatabase().alarmDao().insert(alarm);
                okFlag = true;
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (okFlag) {
                    okFlag = false;

                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();

                    scheduleNotification(getNotification(alarm.getUserName(), alarm.getAlarmName()));

                    ((MainActivity) getContext()).setToolbarTitle("Alarms");
                    HomeFragment homeFragment = new HomeFragment();
                    ((MainActivity) getContext()).changeFragment(homeFragment);
                } else {
                    Toast.makeText(getContext(), "Please fill all field!", Toast.LENGTH_LONG).show();
                }
            }
        }
        SaveTask saveTask = new SaveTask();
        saveTask.execute();
    }

    private void scheduleNotification (Notification notification) {
        Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, NotificationPublisher.ID);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION ,notification);

        int id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),
                                                                 id,
                                                                 notificationIntent,
                                                                 PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager =(AlarmManager) getContext().getSystemService(Context. ALARM_SERVICE ) ;

        assert alarmManager != null;

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public Notification getNotification(String title, String text)
    {
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                                                          .setSmallIcon(R.drawable.ic_format_list)
                                                          .setContentTitle(title)
                                                          .setContentText(text)
                                                          .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                          .setCategory(NotificationCompat.CATEGORY_ALARM)
                                                          .build();

        return notification;
    }

}
