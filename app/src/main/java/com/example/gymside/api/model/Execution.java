package com.example.gymside.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Execution {
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("wasModified")
    @Expose
    private boolean wasModified;

    public Execution(){

    }

    public Execution(Integer duration, boolean wasModified){
        super();
        this.duration = duration;
        this.wasModified = wasModified;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setWasModified(boolean wasModified) {
        this.wasModified = wasModified;
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean isWasModified() {
        return wasModified;
    }
}
