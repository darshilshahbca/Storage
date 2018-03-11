package com.example.x_men.storage.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.x_men.storage.model.DataItem;

import java.util.List;

@Dao
public interface DataItemDAO {

    @Insert
    void insertAll(List<DataItem> items);


    @Insert
    void insertAll(DataItem... items);

    @Query("SELECT COUNT(*) from dataitem")
    int countItems();
}
