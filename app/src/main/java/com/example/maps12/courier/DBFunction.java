package com.example.maps12.courier;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

public class DBFunction {

    /*
    Constants for creating and reading DB
    * */

    protected static final String DB_NAME = "Client2.db";
    protected static final int DB_VERSION = 1;
    protected static final String DB_TABLE = "List";
    protected static final String COLUMN_ID = "_id";
    protected static final String ORDER_NUMBER = "OrderNumber";
    protected static final String PRODUCT_NAME = "ProductName";
    //protected static final String FIRST_NAME = "FirstName";
    protected static final String NAME = "Name";
    protected static final String PHONE_NUMBER = "PhoneNumber";
    protected static final String STREET = "Street";
    protected static final String HOUSE_NUMBER = "HouseNumber";
    protected static final String APARTMENT_NUMBER = "ApartmentNumber";
    protected static final String DELIVERY_TIME = "DeliveryTime";
    protected static final String GPS_ALTITUDE = "gpsAltitude";
    protected static final String GPS_LONGITUDE = "gpsLongitude";
    protected static final String DELIVER = "Deliver";


    protected Context mCtx;

    /*
    Constant with code for creating DB
    * */

    protected static final String DB_CREATE =

            ("CREATE TABLE " + DB_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ORDER_NUMBER + " INTEGER,"
            + PRODUCT_NAME + " TEXT,"
            + NAME + " TEXT,"
            + PHONE_NUMBER + " INTEGER,"
            + STREET + " TEXT,"
            + HOUSE_NUMBER + " INTEGER,"
            + APARTMENT_NUMBER + " INTEGER,"
            + DELIVERY_TIME + " TEXT,"
            + GPS_ALTITUDE + " REAL,"
            + GPS_LONGITUDE + " REAL,"
            + DELIVER + " NUMERIC"
            +" );");


    protected DBHelper mDBHelper;
    protected SQLiteDatabase mDB;


    public DBFunction(Context ctx) {
       mCtx = ctx;
    }

    public DBFunction() {

    }

    /*
    Methods for opening, closing and reading DB
    * */
    public void open() {
       //mDBHelper = new DBHelper(mCtx, Environment.getExternalStorageDirectory()+ File.separator + DB_NAME, null, DB_VERSION);
       mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public Cursor getById(int id) {

        return mDB.query(DB_TABLE,
            new String[] {GPS_ALTITUDE, GPS_LONGITUDE},
            "_id = ?",
            new String[] {Integer.toString(id)},
            null, null, null);
    }

    public Cursor getDeliver (){
        return mDB.query(DB_TABLE,
            null,
            "Deliver = ?",
            new String[] {"1"},
            null, null, null);
    }

    public Cursor getNotDeliver (){
        return mDB.query(DB_TABLE,
                null,
                "Deliver = ?",
                new String[] {"0"},
                null, null, null);}

}
