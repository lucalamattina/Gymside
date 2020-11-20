package com.example.gymside.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credentials {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @Expose
    private String fullName;
    @Expose
    private String email;
    @Expose
    private int birthdate;
    @Expose
    private String gender;

    public Credentials() {
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Credentials(String username, String password, String fullName, String email, int birthdate, String gender) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
