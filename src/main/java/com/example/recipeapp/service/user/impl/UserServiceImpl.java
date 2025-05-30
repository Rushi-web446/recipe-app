package com.example.recipeapp.service.user.impl;

import com.example.recipeapp.dto.UserDto;
import com.example.recipeapp.model.Chef;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.Role;
import com.example.recipeapp.model.User;
import com.example.recipeapp.repository.chef.ChefRepository;
import com.example.recipeapp.repository.user.UserRepository;
import com.example.recipeapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${upload.dir}")
    private String uploadDir;


    @Override
    public boolean isEmailAlreadyRegister(String email) {
        return userRepository.getUserByEmail(email) != null;
    }

    @Override
    @Transactional
    public String registerUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 👈 Encrypted
        user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));
        user.setDateOfRegister(LocalDate.now());
        user = userRepository.save(user); // must save first to generate ID


        // Step 2: Create Chef and link to User
        Chef chef = new Chef();
        chef.setUser(user); // @MapsId will pick user.id as chef.id
        chef.setExperience(userDto.getExperience());

        chefRepository.save(chef);

        return "Successfully added data";
    }

    @Override
    public List<Recipe> getSavedRecipesByUserId(Long userId) {
        return userRepository.getSavedRecipesByUserId(userId);
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }


    @Override
    public String saveRecipeToUser(Long userId, Long recipeId) {
        return userRepository.saveRecipeToUser(userId, recipeId);
    }

    @Override
    public String deleteRecipeToUser(Long userId, Long recipeId) {
        return userRepository.deleteRecipeToUser(userId, recipeId);
    }

    @Override
    @Transactional
    public void updateProfile(Long userId, MultipartFile imageFile, String about) {
        try {
            // Fetch the user
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            MultipartFile file = imageFile;

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Save the image to disk
            Path imagePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(imagePath.getParent()); // Create directory if not exist
            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            // Update user entity
            user.setUserImage("/uploads/" + fileName);
            user.setAboutUser(about);

            // Save the updated user
            userRepository.save(user);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save image or update user profile", e);
        }
    }

    @Override
    public User getChefUserByRecipeId(Long recipeId) {
        return  userRepository.getChefUserByRecipeId(recipeId);
    }
}