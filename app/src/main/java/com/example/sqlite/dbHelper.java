package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    public String tableName;
    public dbHelper(@Nullable Context context) {
        super(context, "db_peopleInfo", null, 1);
        tableName = "peopleInfo";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table peopleInfo(name varchar(40) not null, " +
                "email char(30) not null," +
                "mobileNumber char(20) primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
