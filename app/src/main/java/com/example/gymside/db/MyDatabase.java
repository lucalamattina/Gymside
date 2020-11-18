package com.example.gymside.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gymside.db.dao.SportDao;
import com.example.gymside.db.dao.UserDao;
import com.example.gymside.db.entity.SportEntity;
import com.example.gymside.db.entity.UserEntity;

@Database(entities = {SportEntity.class, UserEntity.class}, exportSchema = false, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    abstract public SportDao sportDao();

    abstract public UserDao userDao();
}