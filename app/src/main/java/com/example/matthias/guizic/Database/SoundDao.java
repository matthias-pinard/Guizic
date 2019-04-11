package com.example.matthias.guizic.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SoundDao {
    @Insert
    public long insert(Sound sound);

    @Query("SELECT * FROM sound")
    public List<Sound> getSounds();

    @Query("SELECT * FROM Piste AS p WHERE p.sound_id=(:sound_id)")
    public List<Piste> getPistes(int sound_id);

    @Query("SELECT MAX(uid) FROM sound")
    public int getMaxId();

}
