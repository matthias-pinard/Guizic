package com.example.matthias.guizic.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Parcours {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private String nameParcours;

    @ColumnInfo
    private String nameZone;

    @ColumnInfo
    private int ordre;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNameParcours() {
        return nameParcours;
    }

    public void setNameParcours(String nameParcours) {
        this.nameParcours = nameParcours;
    }

    public String getNameZone() {
        return nameZone;
    }

    public void setNameZone(String nameZone) {
        this.nameZone = nameZone;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public Parcours(String nameParcours, String nameZone, int ordre){
        this.nameParcours = nameParcours;
        this.nameZone = nameZone;
        this.ordre = ordre;
    }
}