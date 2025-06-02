package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Recipe {
    private UUID id;
    private String name;
    private String description;
    private int duration;
    private ArrayList<String> steps;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<String> categories;
    private User author;
    private RecipeStatus status;
    private ArrayList<Rating> ratings;

    public Recipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredients> ingredients, ArrayList<String> categories, User author, RecipeStatus status) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.steps = new ArrayList<String>();
        this.ingredients = new ArrayList<Ingredients>();
        this.categories = new ArrayList<String>();
        this.author = author;
        this.status = status;
        this.ratings = new ArrayList<Rating>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public void addStep(String step) {
        this.steps.add(step);
    }

    public void removeStep(String step) {
        this.steps.remove(step);
    }

    public void updateStep(int index, String step) {
        this.steps.set(index, step);
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredients ingredient) {
        this.ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredients ingredient) {
        this.ingredients.remove(ingredient);
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public RecipeStatus getStatus() {
        return status;
    }

    public void setStatus(RecipeStatus status) {
        this.status = status;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    public void calculateCost() {

    }

    public void getAverageRating() {

    }

    public void getSimilarRecipes() {

    }
}