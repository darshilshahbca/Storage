package com.example.x_men.storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.x_men.storage.database.DBHelper;
import com.example.x_men.storage.model.DataItem;

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

    public DataItem createItem(DataItem item){
        ContentValues values = item.toValues ();
        mDatabase.insert (ItemsTable.TABLE_ITEMS, null, values);
        return item;
    }

    public long getDataItemsCount(){
        return DatabaseUtils.queryNumEntries (mDatabase, ItemsTable.TABLE_ITEMS);
    }
}
