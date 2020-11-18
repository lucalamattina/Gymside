package com.example.gymside.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.gymside.db.entity.SportEntity;

@Dao
public abstract class SportDao {

    @Query("SELECT * FROM Sport")
    public abstract LiveData<List<SportEntity>> findAll();

    @Query("SELECT * FROM Sport LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<SportEntity>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(SportEntity... sport);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<SportEntity> sports);

    @Update
    public abstract void update(SportEntity sport);

    @Delete
    public abstract void delete(SportEntity sport);

    @Query("DELETE FROM Sport WHERE id = :id")
    public abstract void delete(int id);

    @Query("DELETE FROM Sport WHERE id IN (SELECT id FROM Sport LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Sport")
    public abstract void deleteAll();

    @Query("SELECT * FROM Sport WHERE id = :id")
    public abstract LiveData<SportEntity> findById(int id);
}