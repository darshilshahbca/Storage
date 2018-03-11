package com.example.x_men.storage.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.provider.ContactsContract;

import com.example.x_men.storage.model.DataItem;

@Database (entities = {DataItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static  AppDatabase instance;

    public abstract DataItemDAO dataItemDAO();


    public static AppDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder (context.getApplicationContext (),
                    AppDatabase.class, "app-database")
                    .allowMainThreadQueries ()
                    .build ();
        }
        return instance;
    }

    public static void destoryInstance(){
        instance = null;
    }

}
