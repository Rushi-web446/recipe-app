package com.example.recipeapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "needed_things")
public class NeededThing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private String quantity;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public NeededThing() {}
    public NeededThing(Long id, String itemName, String quantity, Recipe recipe) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "NeededThing{" +
                "itemName='" + itemName + '\'' +
                '}';
    }
}
