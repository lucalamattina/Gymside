package com.example.gymside.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Execution {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("wasModified")
    @Expose
    private boolean wasModified;
    @SerializedName("routine")
    @Expose
    private Routine routine;

    /**
     * No args constructor for use in serialization
     *
     */
    public Execution() {
    }

    /**
     *
     * @param date
     * @param duration
     * @param routine
     * @param wasModified
     * @param id
     * @param userId
     */
    public Execution(int id, int userId, int date, int duration, boolean wasModified, Routine routine) {
        super();
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.duration = duration;
        this.wasModified = wasModified;
        this.routine = routine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isWasModified() {
        return wasModified;
    }

    public void setWasModified(boolean wasModified) {
        this.wasModified = wasModified;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

}