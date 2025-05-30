package com.example.recipeapp.service.chef.impl;

import com.example.recipeapp.model.Chef;
import com.example.recipeapp.model.User;
import com.example.recipeapp.repository.chef.ChefRepository;
import com.example.recipeapp.service.chef.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class ChefServiceImpl implements ChefService {

    @Autowired
    ChefRepository chefRepository;

    @Value("${upload.dir}")
    private String uploadDir;



    @Override
    public Chef getChefByChefId(Long userId) {
        return chefRepository.getChefByChefId(userId);
    }

    @Override
    @Transactional
    public String deleteUploadedRecipe(Chef chef, Long recipeId) {
        return  chefRepository.deleteUploadedRecipe(chef, recipeId);
    }

    @Override
    public String updateUploadedRecipe(Chef chef, Long recipeId) {
        return  chefRepository.deleteUploadedRecipe(chef, recipeId);
    }

    @Override
    @Transactional
    public void updateChefProfile(Long chefId, MultipartFile imageFile, String aboutChef, String experience) {
        try {
            // Fetch the user
            Chef chef = chefRepository.findById(chefId)
                    .orElseThrow(() -> new RuntimeException("Chef not found with id: " + chefId));
            MultipartFile file = imageFile;

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Save the image to disk
            Path imagePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(imagePath.getParent()); // Create directory if not exist
            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            // Update user entity
            chef.setChefImage("/uploads/" + fileName);
            chef.setAboutChef(aboutChef);

            // Save the updated user
            chefRepository.save(chef);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save image or update user profile", e);
        }

    }
}
