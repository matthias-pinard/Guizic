package com.example.matthias.guizic.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SecretZone {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private double latitude;

    @ColumnInfo
    private double longitude;

    @ColumnInfo
    private double sensibilite;

    @ColumnInfo
    private String realName;


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    @ColumnInfo
    private String secretName;

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


    public SecretZone(double longitude, double latitude, double sensibilite, String realName, String secretName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sensibilite = sensibilite;
        this.realName = realName;
        this.secretName = secretName;
    }

    public static SecretZone[] populateData() {
        SecretZone[] s = new SecretZone[] {
                new SecretZone(-1.636408, 48.118096, 1.3, "Esir", "E"),
                new SecretZone(-1.638580, 48.114272, 1.3, "Osur", "O"),
                new SecretZone(-1.643484, 48.117491, 1.3, "Diapason", "D"),
                new SecretZone(-1.635913, 48.118464, 1.3, "Cafeteria", "C"),
                new SecretZone(-1.638580, 48.114272, 1.0, "Osur", "Paul"),
        };
        return s;
    }
}
