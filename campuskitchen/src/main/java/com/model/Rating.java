package com.model;

import java.util.Date;

public class Rating {
    
    private User user;
    private int score;
    private Date date;
    private String comment;
    
    public Rating(User user, Recipe recipe, int score, Date date, String comment) {
        this.user = user;
        this.score = score;
        this.date = date;
        this.comment = comment;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
