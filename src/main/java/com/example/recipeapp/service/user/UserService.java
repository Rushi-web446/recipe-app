package com.example.recipeapp.service.user;


import com.example.recipeapp.dto.UserDto;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    boolean isEmailAlreadyRegister(String email);
    String registerUser(UserDto userDto);
    List<Recipe> getSavedRecipesByUserId(Long userId);
    User getUserByEmail(String email);
    String saveRecipeToUser(Long userId, Long recipeId);
    String deleteRecipeToUser(Long userId, Long recipeId);
    void updateProfile(Long userId, MultipartFile imageFile, String aboutUser);
    User getChefUserByRecipeId(Long recipeId);
}
