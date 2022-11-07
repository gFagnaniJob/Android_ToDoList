package com.example.todolist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Event.class, EventCheckedDate.class}, exportSchema = false, version = 7)
@TypeConverters({DateConverter.class})
public abstract class EventDatabase extends RoomDatabase {
    private static final String DB_NAME = "event_db";
    private static EventDatabase instance;

    public static synchronized EventDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), EventDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract EventDao eventDao();
    public abstract EventCheckedDateDao eventCheckedDateDao();
}
