package com.rsmouki.zed.tp3.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    public String login;
    @ColumnInfo(name = "pass")
    public String pass;

    public static User mainUser = new User();

    public User(@NonNull String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public User() {
    }

    @NonNull

    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

