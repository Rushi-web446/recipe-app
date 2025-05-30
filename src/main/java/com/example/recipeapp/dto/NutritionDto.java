package com.example.recipeapp.dto;

public class NutritionDto {
    private String nutrientName;
    private String value;

    // Getters and Setters

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public NutritionDto() {}
    public NutritionDto(String nutrientName, String value) {
        this.nutrientName = nutrientName;
        this.value = value;
    }
}
