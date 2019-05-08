package com.example.matthias.guizic.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.example.matthias.guizic.utils.JsonReaderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


@Database(entities = {SecretZone.class,  Sound.class, Piste.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static String TAG = "DEBUG";
    private static AppDatabase INSTANCE;

    public abstract SecretZoneDao secretZoneDao();
    public abstract SoundDao soundDao();
    public abstract PisteDao pisteDao();

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
                        loadJson(context);
                    }
                });
            }
        };
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "my-database").addCallback(rdc).allowMainThreadQueries().build();
    }

    public static void loadJson(final Context context) {
        Log.d(TAG, "Pre-populate");
        List<SecretZone> lZone = new ArrayList<SecretZone>();
        JsonReaderUtils jsonReader = new JsonReaderUtils(context);
        String json = jsonReader.readJsonFile();
        lZone = jsonReader.parseJsonFileZone();
        SecretZone[] secretZones = {};
        secretZones = lZone.toArray(secretZones);
        AppDatabase db = getInstance(context);

        Sound sound = new Sound("playlist 1");
        db.soundDao().insert(sound);

        db.
                secretZoneDao().
                insertAll(secretZones);


        Log.d(TAG, "Pre-populated");
    }
}