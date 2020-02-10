package com.example.czirjktibor.reminderapp.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient INSTANCE;

    private AppDatabase appDatabase;

    public DatabaseClient(Context ctx) {
        this.context = ctx;

        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "alarmdb").build();
    }

    public static synchronized DatabaseClient getInstance(Context ctx){
        if (INSTANCE == null) {
            INSTANCE = new DatabaseClient(ctx);
        }
        return INSTANCE;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
