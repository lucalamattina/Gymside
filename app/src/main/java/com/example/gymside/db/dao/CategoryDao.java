package com.example.gymside.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gymside.db.entity.CategoryEntity;

import java.util.List;

public abstract class CategoryDao {

    @Query("SELECT * FROM Sport")
    public abstract LiveData<List<CategoryEntity>> findAll();

    @Query("SELECT * FROM Sport LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<CategoryEntity>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CategoryEntity... category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<CategoryEntity> category);

    @Update
    public abstract void update(CategoryEntity category);

    @Delete
    public abstract void delete(CategoryEntity category);

    @Query("DELETE FROM Category WHERE id = :id")
    public abstract void delete(int id);

    @Query("DELETE FROM Category WHERE id IN (SELECT id FROM Sport LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Category")
    public abstract void deleteAll();

    @Query("SELECT * FROM Category WHERE id = :id")
    public abstract LiveData<CategoryEntity> findById(int id);
}
