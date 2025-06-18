package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a Recipe
 */
public class Recipe {
    private UUID id;
    private String name;
    private String description;
    private int duration;
    private ArrayList<String> steps;
    private ArrayList<Ingredient> ingredient;
    private ArrayList<Culture> culture;
    private ArrayList<Dietary> dietary;
    private ArrayList<Course> course;
    private User author;
    private RecipeStatus status;
    private ArrayList<Rating> ratings;
    private ArrayList<String> ratingIds;
    private ArrayList<Category> categories;
    private String imagePath;

    // add default constructor
    /**
     * Constructs new Recipe with UUID
     * @param name Recipe name
     * @param description Recipe desciption
     * @param duration Prep time in minutes
     * @param steps list of prepartion steps
     * @param ingredient list of ingredients
     * @param culture classification of recipe
     * @param dietary dietary restrictions
     * @param course course of recipe
     * @param author user who created the recipe
     * @param status approval status
     */
    public Recipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredient,
                    ArrayList<Culture> culture, ArrayList<Dietary> dietary, ArrayList<Course> course, User author, RecipeStatus status) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.steps = new ArrayList<String>();
        this.ingredient = new ArrayList<Ingredient>();
        this.culture = new ArrayList<Culture>();
        this.dietary = new ArrayList<Dietary>();
        this.course = new ArrayList<Course>();
        this.author = author;
        this.status = status;
        this.ratings = new ArrayList<Rating>();
   //     this.ingredient = (ingredient != null) ? new ArrayList<>(ingredient) : new ArrayList<>();
    }

    /**
     * Overloaded constructor to include UUID
     */
    public Recipe(UUID id, String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredient,
                    ArrayList<String> categories, User author, RecipeStatus status, ArrayList<Rating> ratings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.steps = new ArrayList<String>();
        this.ingredient = new ArrayList<Ingredient>();
        this.culture = new ArrayList<Culture>();
        this.dietary = new ArrayList<Dietary>();
        this.course = new ArrayList<Course>();
        this.author = author;
        this.status = status;
        this.ratings = new ArrayList<Rating>();
    }

    public Recipe(String name, String description, int duration, ArrayList<String> steps, ArrayList<Ingredient> ingredients, 
                    ArrayList<Category> categories, User author, RecipeStatus status) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.description = description;
    this.duration = duration;
    this.steps = steps;
    this.ingredient = ingredients;
    this.categories = categories;
    this.author = author;
    this.status = status;
    this.ratings = new ArrayList<>();
}

    /** 
     * Setters and getters
     */
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
        this.steps = (steps == null) ? new ArrayList<String>() : steps;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredient;
    }

    public void setIngredients(ArrayList<Ingredient> ingredient) {
        this.ingredient = (ingredient != null) ? new ArrayList<>(ingredient) : new ArrayList<>();
    }

    public ArrayList<Culture> getCulture() {
        return culture;
    }

    public void setCulture(ArrayList<Culture> culture) {
        this.culture = (culture == null) ? new ArrayList<Culture>() : culture;
    }

    public ArrayList<Dietary> getDietary() {
        return dietary;
    }

    public void setDietary(ArrayList<Dietary> dietary) {
        this.dietary = (dietary == null) ? new ArrayList<Dietary>() : dietary;
    }

    public ArrayList<Course> getCourse() {
        return course;
    }

    public void setCourse(ArrayList<Course> course) {
        this.course = (course == null) ? new ArrayList<Course>() : course;
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

    public ArrayList<String> getRatingIds(){
        return ratingIds;
    }

    public void setRatingIds(ArrayList<String> ratingIds){
        this.ratingIds = ratingIds;
    }

    /**
     * Adds new step to preparation list
     * @param step Step desciption to add
     */
    public void addStep(String step) {
        this.steps.add(step);
    }

    /**
     * Removes specified step
     * @param step Step desciption to remove
     */
    public void removeStep(String step) {
        this.steps.remove(step);
    }

    /**
     * Updates particular step
     * @param index index of step
     * @param step new text for step
     */
    public void updateStep(int index, String step) {
        this.steps.set(index, step);
    }

    /**
     * Adds an ingredient to recipe
     * @param ingredient Ingredient to add
     */
    public void addIngredient(Ingredient ingredient) {
        this.ingredient.add(ingredient);
    }

    /**
     * Removes ingredient from recipe
     * @param ingredient Ingredient to remove
     */
    public void removeIngredient(Ingredient ingredient) {
        this.ingredient.remove(ingredient);
    }

    /**
     * Calculates average score of all ratings
     * @return Average rating
     */
    public double getAverageRating() {
        if(ratings.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Rating r : ratings) {
            sum += r.getScore();
        }
        return (double) sum / ratings.size();
    }

    /**
     * Finds similar recipes based on ingredients
     */
    public void getSimilarRecipes() {

    }

    /**
     * Grovery list from recipe's ingredients
     * @return List of ingredients
     */
    public ArrayList<Ingredient> generateGroceryList() {
        return new ArrayList<>(this.ingredient);
    }

    /**
     * Renames recipe
     * @param name new name of recipe
     */
    public void rename(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Recipe{name='" + name + "', author=  " + author.getUsername() + "', description='" + description + "', id=" + id + "', ratings: " + ratings + "}";
    }

    public String[] getCategories() {
        String[] categories = {"Culture", "Dietary", "Course"};
        return categories;
    }

    // Add getter and setter:
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
