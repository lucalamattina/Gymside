package com.example.gymside.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Sport", indices = {@Index("id")}, primaryKeys = {"id"})
public class SportEntity {

    @NonNull
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "detail")
    public String detail;

    public SportEntity(int id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }
}