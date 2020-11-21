package com.example.gymside.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("review")
    @Expose
    private String review;


    public Rating(int score, String review) {
        this.score = score;
        this.review = review;
    }

    public int getScore() {
        return score;
    }

    public void setScore(Integer id) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
