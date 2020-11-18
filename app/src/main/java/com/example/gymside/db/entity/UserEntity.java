package com.example.gymside.db.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "User", indices = {@Index("id")}, primaryKeys = {"id"})
public class UserEntity {
    @NonNull
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "username")
    public String name;
    @ColumnInfo(name = "gender")
    public String gender;
    @ColumnInfo(name = "avatarUrl")
    public String avatarUrl;
    @ColumnInfo(name = "dateCreated")
    public int dateCreated;
    @ColumnInfo(name = "dateLastActive")
    public int dateLastActive;

    public UserEntity(int id, String name, String gender, String avatarUrl, int dateCreated, int dateLastActive) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
    }
}
