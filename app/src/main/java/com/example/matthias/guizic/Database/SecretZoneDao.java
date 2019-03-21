package com.example.matthias.guizic.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SecretZoneDao {
    @Query("Select * from SecretZone")
    List<SecretZone> getAll();

    @Query("Select * from SecretZone where uid=(:uid)")
    SecretZone findById(int uid);

    @Query("Select uid from SecretZone where realName=(:name)")
    int findIdByName(String name);

    @Insert
    void insertAll(SecretZone... secretZones);

    @Delete
    void delete(SecretZone secretZone);

    @Query("DELETE FROM SecretZone")
    void deleteAll();
}
