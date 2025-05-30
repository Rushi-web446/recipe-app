package com.example.recipeapp.repository.chef;

import com.example.recipeapp.model.Chef;

public interface ChefCustomRepository {
    Chef getChefByChefId(Long userId);
    String deleteUploadedRecipe(Chef chef, Long recipeId);

}
