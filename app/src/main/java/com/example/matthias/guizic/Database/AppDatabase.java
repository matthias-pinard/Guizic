package com.example.matthias.guizic.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.Executors;


@Database(entities = {SecretZone.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static String TAG = "DEBUG";
    private static AppDatabase INSTANCE;

    public abstract SecretZoneDao secretZoneDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
            public void onCreate(SupportSQLiteDatabase db) {
                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Pre-populate");
                        getInstance(context).
                                secretZoneDao().
                                insertAll(SecretZone.
                                        populateData());
                        Log.d(TAG, "Pre-populated");
                    }
                });
            }
        };
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "my-database").addCallback(rdc).allowMainThreadQueries().build();
    }

}