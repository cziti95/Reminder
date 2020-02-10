package com.example.czirjktibor.reminderapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import com.example.czirjktibor.reminderapp.entitys.Alarm;
import com.example.czirjktibor.reminderapp.utils.ColorConverter;
import com.example.czirjktibor.reminderapp.utils.DateConverter;

@Database(entities = {Alarm.class}, version = 1)
@TypeConverters({DateConverter.class, ColorConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDao alarmDao();
}
