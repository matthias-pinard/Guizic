package com.example.matthias.guizic.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Parcours {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private int idparcours;

    @ColumnInfo
    private int idzone;

    @ColumnInfo
    private int ordre;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ColumnInfo
    private String name;

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIdparcours() {
        return idparcours;
    }

    public void setIdparcours(int idparcours) {
        this.idparcours = idparcours;
    }

    public int getIdzone() {
        return idzone;
    }

    public void setIdzone(int idzone) {
        this.idzone = idzone;
    }

    public Parcours(int idparcours, int idzone, int ordre, String name){
        this.idparcours = idparcours;
        this.idzone = idzone;
        this.ordre = ordre;
        this.name = name;
    }
}