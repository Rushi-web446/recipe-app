package com.example.recipeapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_process")
public class RecipeProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stepNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public RecipeProcess() {}
    public RecipeProcess(Long id, int stepNumber, String description, Recipe recipe) {
        this.id = id;
        this.stepNumber = stepNumber;
        this.description = description;
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "RecipeProcess{" +
                "stepNumber=" + stepNumber +
                '}';
    }

}
