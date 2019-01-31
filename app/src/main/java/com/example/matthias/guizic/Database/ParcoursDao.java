package com.example.matthias.guizic.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ParcoursDao {
    @Query("Select * from Parcours")
    List<SecretZone> getAll();

    @Query("Select * from SecretZone Where uid = " +
            "(Select uid from Parcours Where nameParcours=(:name) Order By ordre)")
    List<SecretZone> getParcours(String name);

    @Delete
    void delete(Parcours parcours);


}
