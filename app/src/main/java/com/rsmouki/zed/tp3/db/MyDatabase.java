package com.rsmouki.zed.tp3.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {User.class,Etablissement.class}, version = 2)
public abstract class MyDatabase extends RoomDatabase {
    private static final String TAG = "ROOM DATABASE";
    //Log.d(TAG, "create usrDao");
    public abstract usrDAO userDao();
    public abstract etabDAO etabDao();
}