package com.rsmouki.zed.tp3.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "etablissements")
public class Etablissement {
    @PrimaryKey
    @NonNull
    public String nom;
    @ColumnInfo(name = "desc")
    public String desc;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] image;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double lat;
    public double lng;


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Etablissement(@NonNull String nom, String desc) {
        this.nom = nom;
        this.desc = desc;
    }
    public Etablissement(@NonNull String nom,byte[] img, String desc,Double lat, Double lng) {
        this.nom = nom;
        this.desc = desc;
        this.image = img;
        this.lat = lat;
        this.lng = lng;
    }

    public Etablissement() {
    }

    @NonNull

    public String getnom() {
        return nom;
    }

    public void setnom(@NonNull String nom) {
        this.nom = nom;
    }

    public String getdesc() {
        return desc;
    }

    public void setdesc(String desc) {
        this.desc = desc;
    }



}
