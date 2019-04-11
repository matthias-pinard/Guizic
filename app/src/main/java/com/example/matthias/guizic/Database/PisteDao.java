package com.example.matthias.guizic.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface PisteDao {
    @Insert
    void insert(Piste... pistes);
}
