package com.example.recipeapp.repository.recipe;

import com.example.recipeapp.model.Chef;
import com.example.recipeapp.model.Recipe;

import java.util.List;

public interface RecipeCustomRepository {
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Long id);
    void save(Recipe recipe, Chef chef);
    List<Recipe> findByFilters(List<String> mealTypeList, List<String> cuisineTypeList);

//    void update(Recipe updatedRecipe);


    }
