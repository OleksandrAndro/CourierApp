package com.example.maps12.courier;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Environment;

import java.io.File;


public class DBHelper extends SQLiteOpenHelper {

    DBFunction function = new DBFunction();



    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
        //SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + File.separator + name,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(function.DB_CREATE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
