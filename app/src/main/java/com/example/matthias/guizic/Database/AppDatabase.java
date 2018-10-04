package com.example.matthias.guizic.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {SecretZone.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SecretZoneDao secretZoneDao();
}
