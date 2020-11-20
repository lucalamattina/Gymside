package com.example.gymside.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.gymside.api.model.Category;
import com.example.gymside.db.entity.CategoryEntity;

@Entity(tableName = "Routine", indices = {@Index("id")}, primaryKeys = {"id"})
public class RoutineEntity {
    @NonNull
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "detail")
    public String detail;
    @ColumnInfo(name = "dateCreated")
    public int dateCreated;
    @ColumnInfo(name = "averageRating")
    public float averageRating;
    @ColumnInfo(name = "isPublic")
    public boolean isPublic;
    @ColumnInfo(name = "category")
    public int category;
    @ColumnInfo(name = "difficulty")
    public String difficulty;
    
    public RoutineEntity(int id, String name, String detail, int dateCreated, float averageRating, boolean isPublic, int category, String difficulty){
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.dateCreated = dateCreated;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.category = category;
        this.difficulty = difficulty;
    }
    
}

