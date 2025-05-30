package com.example.recipeapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String nameDescription;

    @Column(columnDefinition = "TEXT")
    private String aboutRecipe;

    private String recipeImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type")
    private MealType mealType;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type")
    private CuisineType cuisineType;

    private int timeTakeForMade;

    private LocalDate uploadedDate;
    private LocalDate lastUpdatedDate;

    @ManyToMany(mappedBy = "savedRecipes")
    private List<User> likedByUsers;

    @ManyToMany(mappedBy = "uploadedRecipes")
    private List<Chef> chefs;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeProcess> process;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NeededThing> neededThings;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nutrition> nutrition;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDescription() {
        return nameDescription;
    }

    public void setNameDescription(String nameDescription) {
        this.nameDescription = nameDescription;
    }

    public String getAboutRecipe() {
        return aboutRecipe;
    }

    public void setAboutRecipe(String aboutRecipe) {
        this.aboutRecipe = aboutRecipe;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public int getTimeTakeForMade() {
        return timeTakeForMade;
    }

    public void setTimeTakeForMade(int timeTakeForMade) {
        this.timeTakeForMade = timeTakeForMade;
    }

    public LocalDate getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDate uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public List<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(List<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public List<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(List<Chef> chefs) {
        this.chefs = chefs;
    }

    public List<RecipeProcess> getProcess() {
        return process;
    }

    public void setProcess(List<RecipeProcess> process) {
        this.process = process;
    }

    public List<NeededThing> getNeededThings() {
        return neededThings;
    }

    public void setNeededThings(List<NeededThing> neededThings) {
        this.neededThings = neededThings;
    }

    public List<Nutrition> getNutrition() {
        return nutrition;
    }

    public void setNutrition(List<Nutrition> nutrition) {
        this.nutrition = nutrition;
    }

    public Recipe() {
        this.neededThings = new ArrayList<>();
        this.nutrition = new ArrayList<>();
        this.process = new ArrayList<>();
        this.chefs = new ArrayList<>();

    }
    public Recipe(Long id, String name, String nameDescription, String aboutRecipe, String recipeImage, MealType mealType, CuisineType cuisineType, int timeTakeForMade, LocalDate uploadedDate, LocalDate lastUpdatedDate, List<User> likedByUsers, List<Chef> chefs, List<RecipeProcess> process, List<NeededThing> neededThings, List<Nutrition> nutrition) {
        this.id = id;
        this.name = name;
        this.nameDescription = nameDescription;
        this.aboutRecipe = aboutRecipe;
        this.recipeImage = recipeImage;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.timeTakeForMade = timeTakeForMade;
        this.uploadedDate = uploadedDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.likedByUsers = likedByUsers;
        this.chefs = chefs;
        this.process = process;
        this.neededThings = neededThings;
        this.nutrition = nutrition;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", nameDescription='" + nameDescription + '\'' +
                ", uploadedDate=" + uploadedDate +
                '}';
    }
}
