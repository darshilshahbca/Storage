package com.example.x_men.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.x_men.storage.database.DBHelper;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public DataSource(Context mContext) {
        this.mContext = mContext;
        mDbHelper = new DBHelper (mContext);
        mDatabase = mDbHelper.getWritableDatabase ();
    }

    public void open(){
        mDatabase = mDbHelper.getWritableDatabase ();
    }

    public void close(){
        mDbHelper.close ();
    }
}
