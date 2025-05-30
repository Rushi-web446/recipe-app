package com.example.recipeapp.service.recipe;

import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.Chef;
import com.example.recipeapp.dto.RecipeUploadDto;


import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Long id);

    void saveRecipeFromDto(RecipeUploadDto dto, Chef chef);

    void updateUploadedRecipe(RecipeUploadDto dto, Long recipeId, Chef chef);

    List<Recipe> findByFilters(List<String> mealTypeList, List<String> cuisineTypeList);
}