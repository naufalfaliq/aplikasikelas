package com.example.aplikasiclass.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.aplikasiclass.model.ClassBaruModel;
import com.example.aplikasiclass.model.ClassModel;

@Database(entities = {ClassModel.class}, version = 1)
public abstract class ClassDatabase extends RoomDatabase {

    public abstract ClassDao kelasDao();

    private static ClassDatabase INSTANCE;

    // Membuat method return untuk membuat database
    public static ClassDatabase createDatabase(Context context){
        synchronized (ClassDatabase.class){
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, ClassDatabase.class, "db_class")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}