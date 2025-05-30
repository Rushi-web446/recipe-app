package com.example.recipeapp.service.chef;

import com.example.recipeapp.model.Chef;
import org.springframework.web.multipart.MultipartFile;

public interface ChefService {
    Chef getChefByChefId(Long chefId);
    String deleteUploadedRecipe(Chef chef, Long recipeId);
    String updateUploadedRecipe(Chef chef, Long recipeId);
    void updateChefProfile(Long userId, MultipartFile imageFile, String aboutchef, String experience);

}
