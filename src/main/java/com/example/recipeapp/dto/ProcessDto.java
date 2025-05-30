package com.example.recipeapp.dto;

public class ProcessDto {
    private int stepNumber;
    private String description;

    // Getters and Setters

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProcessDto() {}
    public ProcessDto(int stepNumber, String description) {
        this.stepNumber = stepNumber;
        this.description = description;
    }
}
