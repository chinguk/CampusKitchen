package com.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents user's rating of a recipe
 */
public class Rating {
    private String userId;
    private String recipeId;
    private User user;
    private int score;
    private Date date;
    private String comment;
    
    /**
     * Constructs a Rating for a Recipe
     * @param user User who submitted the rating
     * @param recipe Recipe being rated
     * @param score Score assigned
     * @param date Date of submission
     * @param comment Comment text
     */
    public Rating(User user, Recipe recipe, int score, Date date, String comment) {
        this.user = user;
        this.score = score;
        this.date = date;
        this.comment = comment;
    }

    /**
     * Constructs rating by user and recipe ID
     * @param userId ID of user submitting rating
     * @param recipeId ID of recipe being rated
     * @param score score assigned
     * @param date Date of submission
     * @param comment Comment text
     */
    public Rating(String userId, String recipeId, int score, Date date, String comment) {
        this.userId   = userId;
        this.recipeId = recipeId;
        this.score    = score;
        this.date     = date;
        this.comment  = comment;
    }

    /**
     * Returns ID of user who submitted the rating
     * @return user ID
     */
    public String getUser() {
        return userId;
    }

    /**
     * Returns ID of recipe that was rated
     * @return Recipe ID
     */
    public String getRecipe() {
        return recipeId;
    }

    /**
     * Returns Date when rating was submitted
     * @return Submission date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns comment provided with rating
     * @return Comment text
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns numeric score of rating
     * @return Score value
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets user ID for rating
     * @param userId new user ID
     */
    public void setUser (String userId) {
        this.userId = userId;
    }

    /**
     * Set recipe ID for rating
     * @param recipeId new recipe ID
     */
    public void setRecipe (String recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Sets Score for rating
     * @param score new score value
     */
    public void setScore (int score) {
        this.score = score;
    }

    /**
     * Sets date for rating
     * @param date new submission date
     */
    public void setDate (Date date) {
        this.date = date;
    }

    /**
     * Sets comment for rating
     * @param comment new comment text
     */
    public void setComment (String comment) {
        this.comment = comment;
    }

    /**
     * Returns string representation of Rating
     */
    @Override
    public String toString() {
        return "Rating{" + "userId='" + userId + '\'' + ", recipeId='" + recipeId + '\'' + ", score=" + score + ", date=" + date + ", comment='" + comment + '\'' + '}';
    }

    public static ArrayList<Rating> emptyList() {
        return new ArrayList<>();
    }
}
