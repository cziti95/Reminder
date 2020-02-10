package com.example.czirjktibor.reminderapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.example.czirjktibor.reminderapp.entitys.Alarm;
import java.util.List;

@Dao
public interface AlarmDao {

    @Query("SELECT * FROM alarm")
    List<Alarm> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Alarm alarm);

    @Delete
    void delete(Alarm alarm);
}
