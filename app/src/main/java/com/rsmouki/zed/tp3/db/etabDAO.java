package com.rsmouki.zed.tp3.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface etabDAO {
    @Insert(onConflict=OnConflictStrategy.IGNORE)
    public void addEtablissement(Etablissement etablissement);
    @Query("select * from etablissements where nom = :nomEtablissment")
    public List<Etablissement> getEtablissement(String nomEtablissment);
    @Query("select * from etablissements")
    public List<Etablissement> getEtablissement();
    @Delete
    public void deletEtablissement(Etablissement etablissement);
    @Update
    public void updatEtablissement(Etablissement etablissement);
    
}