package com.example.recipeapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nutrition")
public class Nutrition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nutrientName;
    private String value;

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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public Nutrition() {}
    public Nutrition(Long id, String nutrientName, String value, Recipe recipe) {
        this.id = id;
        this.nutrientName = nutrientName;
        this.value = value;
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "Nutrition{" +
                "id=" + id +
                ", nutrientName='" + nutrientName + '\'' +
                ", value='" + value + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
