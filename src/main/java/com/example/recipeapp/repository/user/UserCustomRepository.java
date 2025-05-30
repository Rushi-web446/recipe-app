package com.example.recipeapp.repository.user;

import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserCustomRepository {
    User getUserByEmail(String email);

    List<Recipe> getSavedRecipesByUserId(Long userId);

    String saveRecipeToUser(Long userId, Long recipeId);
    String deleteRecipeToUser(Long userId, Long recipeId);
//    String updateProfile(Long userId, MultipartFile imageFile, String aboutUser);
    User getChefUserByRecipeId(Long recipeId);
}
