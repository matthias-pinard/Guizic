package com.example.matthias.guizic.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SecretZone {
    @PrimaryKey
    private int uid;

    @ColumnInfo
    private double latitude;

    @ColumnInfo
    private double longitude;

    @ColumnInfo
    private double sensibilite;

    @ColumnInfo
    private String RealName;

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getSecretName() {
        return SecretName;
    }

    public void setSecretName(String secretName) {
        SecretName = secretName;
    }

    @ColumnInfo
    private String SecretName;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getSensibilite() {
        return sensibilite;
    }

    public void setSensibilite(double sensibilite) {
        this.sensibilite = sensibilite;
    }
}
