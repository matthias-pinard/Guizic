package com.example.matthias.guizic.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Piste{

    public Piste() {

    }
    public Piste(String name, String path, Integer sound_id){
        this.name = name;
        this.path = path;
        this.soundId = sound_id;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    private String path;

    @ColumnInfo
    private String name;

    @ColumnInfo(name = "sound_id", index = true)
    private Integer soundId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSoundId() {
        return soundId;
    }

    public void setSoundId(Integer soundId) {
        this.soundId = soundId;
    }
}
