package com.example.recipeapp.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;



public class RecipeUploadDto {
    private String name;
    private String nameDescription;
    private String aboutRecipe;
    private String mealType;        // Enum or String
    private String cuisineType;     // Enum or String
    private int timeTakeForMade;

    private MultipartFile imageFile;

    private List<NeededThingDto> neededThings = new ArrayList<>();
    private List<NutritionDto> nutrition = new ArrayList<>();
    private List<ProcessDto> process = new ArrayList<>();

    // Getters and Setters

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

    public MultipartFile  getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile  image) {
        this.imageFile = image;
    }

    public List<NeededThingDto> getNeededThings() {
        return neededThings;
    }

    public void setNeededThings(List<NeededThingDto> neededThings) {
        this.neededThings = neededThings;
    }

    public List<NutritionDto> getNutrition() {
        return nutrition;
    }

    public void setNutrition(List<NutritionDto> nutrition) {
        this.nutrition = nutrition;
    }

    public List<ProcessDto> getProcess() {
        return process;
    }

    public void setProcess(List<ProcessDto> process) {
        this.process = process;
    }

    public String getAboutRecipe() {
        return aboutRecipe;
    }

    public void setAboutRecipe(String aboutRecipe) {
        this.aboutRecipe = aboutRecipe;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public int getTimeTakeForMade() {
        return timeTakeForMade;
    }

    public void setTimeTakeForMade(int timeTakeForMade) {
        this.timeTakeForMade = timeTakeForMade;
    }
}
