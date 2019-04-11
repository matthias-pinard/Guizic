package com.example.matthias.guizic.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Sound {
    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo
    String name;

    public Sound(String name) {
        this.name = name;
    }

}

