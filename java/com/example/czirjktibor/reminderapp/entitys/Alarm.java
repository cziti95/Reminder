package com.example.czirjktibor.reminderapp.entitys;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "alarm")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alarm_ID")
    private int alarmID;

    @ColumnInfo(name = "user_name")
    private String userName;

    @NonNull
    @ColumnInfo(name = "name")
    private String alarmName;

    @ColumnInfo(name = "time")
    private Date alarmTime;

    @ColumnInfo(name = "color")
    private Color color;

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Alarm() {
    }

    public Alarm(int alarmID, String userName, @NonNull String alarmName, Date alarmTime, Color color) {
        this.alarmID = alarmID;
        this.userName = userName;
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
        this.color = color;
    }

    public Alarm(String userName, @NonNull String alarmName, Date alarmTime, Color color) {
        this.userName = userName;
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
        this.color = color;
    }
}
