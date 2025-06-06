package com.model;

import java.util.Date;

public class Rating {
    private String userId;
    private String recipeId;
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

    public Rating(String userId, String recipeId, int score, Date date, String comment) {
        this.userId   = userId;
        this.recipeId = recipeId;
        this.score    = score;
        this.date     = date;
        this.comment  = comment;
    }

    public String getUser() {
        return userId;
    }

    public String getRecipe() {
        return recipeId;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public int getScore() {
        return score;
    }

    public void setUser (String userId) {
        this.userId = userId;
    }

    public void setRecipe (String recipeId) {
        this.recipeId = recipeId;
    }

    public void setScore (int score) {
        this.score = score;
    }

    public void setDate (Date date) {
        this.date = date;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating{" + "userId='" + userId + '\'' + ", recipeId='" + recipeId + '\'' + ", score=" + score + ", date=" + date + ", comment='" + comment + '\'' + '}';
    }
}
