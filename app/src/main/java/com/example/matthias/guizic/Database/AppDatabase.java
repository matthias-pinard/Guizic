package com.example.matthias.guizic.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.matthias.guizic.utils.JsonReaderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


@Database(entities = {SecretZone.class,  Parcours.class}, version = 1, exportSchema = false)
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
                        List<SecretZone> lZone = new ArrayList<SecretZone>();
                        JsonReaderUtils jsonReader = new JsonReaderUtils(context);
                        String json = jsonReader.readJsonFile();
                        lZone = jsonReader.parseJsonFile();
                        SecretZone[] secretZones = {};
                        secretZones = lZone.toArray(secretZones);
                        getInstance(context).
                                secretZoneDao().
                                insertAll(secretZones);
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