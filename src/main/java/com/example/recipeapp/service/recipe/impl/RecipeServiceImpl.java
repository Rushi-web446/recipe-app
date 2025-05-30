package com.example.recipeapp.service.recipe.impl;

import com.example.recipeapp.dto.NeededThingDto;
import com.example.recipeapp.dto.NutritionDto;
import com.example.recipeapp.dto.ProcessDto;
import com.example.recipeapp.dto.RecipeUploadDto;
import com.example.recipeapp.model.*;
import com.example.recipeapp.repository.recipe.RecipeRepository;
import com.example.recipeapp.service.recipe.RecipeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.example.recipeapp.dto.RecipeUploadDto;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.Chef;
//import com.example.recipeapp.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;


@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Value("${upload.dir}")
    private String uploadDir;


    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.getAllRecipes();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.getRecipeById(id);
    }

    @Override
    @Transactional
    public void saveRecipeFromDto(RecipeUploadDto dto, Chef chef) {


        try {
            MultipartFile file = dto.getImageFile();

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path imagePath = Paths.get(uploadDir, fileName);

            // Create directory if not exists
            Files.createDirectories(imagePath.getParent());

            // Save file to disk
            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

//        } catch (IOException e) {
//            throw new RuntimeException("Error saving recipe image", e);
//        }


            Recipe recipe = new Recipe();

        // Set simple fields
        recipe.setName(dto.getName());
        recipe.setNameDescription(dto.getNameDescription());
        recipe.setAboutRecipe(dto.getAboutRecipe());
        recipe.setRecipeImage("/uploads/" + fileName); // Path for browser
        recipe.setTimeTakeForMade(dto.getTimeTakeForMade());
        recipe.setMealType(MealType.valueOf(dto.getMealType()));
        recipe.setCuisineType(CuisineType.valueOf(dto.getCuisineType()));

        // Ingredients
        for (NeededThingDto thingDTO : dto.getNeededThings()) {
            NeededThing thing = new NeededThing();
            thing.setItemName(thingDTO.getItemName());
            thing.setQuantity(thingDTO.getQuantity());
            thing.setRecipe(recipe);
            recipe.getNeededThings().add(thing);
        }

        // Nutrition
        for (NutritionDto nutDTO : dto.getNutrition()) {
            Nutrition nut = new Nutrition();
            nut.setNutrientName(nutDTO.getNutrientName());
            nut.setValue(nutDTO.getValue());
            nut.setRecipe(recipe);
            recipe.getNutrition().add(nut);
        }

        // Process
        for (ProcessDto procDTO : dto.getProcess()) {
            RecipeProcess process = new RecipeProcess();
            process.setStepNumber(procDTO.getStepNumber());
            process.setDescription(procDTO.getDescription());
            process.setRecipe(recipe);
            recipe.getProcess().add(process);
        }

        // Now only ONE save is needed
        System.out.println("\n\n %%% ########  bharat mata ki jai  : " + chef.getId() + "     ::: " + recipe.getId() + "\n\n");

        recipeRepository.save(recipe, chef);

    } catch (IOException e) {
        throw new RuntimeException("Error saving recipe image", e);
    }

}



    @Override
    @Transactional
    public void updateUploadedRecipe(RecipeUploadDto dto, Long recipeId, Chef chef) {
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with ID: " + recipeId));

        // ✅ Ownership check
        if (!existingRecipe.getChefs().contains(chef)) {
            throw new SecurityException("Unauthorized: You do not own this recipe.");
        }

        MultipartFile file = dto.getImageFile();

        try {
            // ✅ Image update only if new image is uploaded
            if (file != null && !file.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path imagePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(imagePath.getParent());
                Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                existingRecipe.setRecipeImage("/uploads/" + fileName);
            }

            // ✅ Update scalar fields
            existingRecipe.setName(dto.getName());
            existingRecipe.setNameDescription(dto.getNameDescription());
            existingRecipe.setAboutRecipe(dto.getAboutRecipe());
            existingRecipe.setTimeTakeForMade(dto.getTimeTakeForMade());
            existingRecipe.setMealType(MealType.valueOf(dto.getMealType()));
            existingRecipe.setCuisineType(CuisineType.valueOf(dto.getCuisineType()));

            // ✅ Clear and update NeededThings (Ingredients)
            if (existingRecipe.getNeededThings() != null) {
                existingRecipe.getNeededThings().forEach(n -> n.setRecipe(null));
                existingRecipe.getNeededThings().clear();
            } else {
                existingRecipe.setNeededThings(new ArrayList<>());
            }

            for (NeededThingDto thingDTO : dto.getNeededThings()) {
                if (thingDTO.getItemName() != null && thingDTO.getQuantity() != null) {
                    NeededThing thing = new NeededThing();
                    thing.setItemName(thingDTO.getItemName());
                    thing.setQuantity(thingDTO.getQuantity());
                    thing.setRecipe(existingRecipe);
                    existingRecipe.getNeededThings().add(thing);
                }
            }

            // ✅ Clear and update Nutrition
            if (existingRecipe.getNutrition() != null) {
                existingRecipe.getNutrition().forEach(n -> n.setRecipe(null));
                existingRecipe.getNutrition().clear();
            } else {
                existingRecipe.setNutrition(new ArrayList<>());
            }

            for (NutritionDto nutDTO : dto.getNutrition()) {
                if (nutDTO.getNutrientName() != null && nutDTO.getValue() != null) {
                    Nutrition nut = new Nutrition();
                    nut.setNutrientName(nutDTO.getNutrientName());
                    nut.setValue(nutDTO.getValue());
                    nut.setRecipe(existingRecipe);
                    existingRecipe.getNutrition().add(nut);
                }
            }

            // ✅ Clear and update Steps / Process
            if (existingRecipe.getProcess() != null) {
                existingRecipe.getProcess().forEach(p -> p.setRecipe(null));
                existingRecipe.getProcess().clear();
            } else {
                existingRecipe.setProcess(new ArrayList<>());
            }

            for (ProcessDto procDTO : dto.getProcess()) {
                if (procDTO.getDescription() != null) {
                    RecipeProcess process = new RecipeProcess();
                    process.setStepNumber(procDTO.getStepNumber());
                    process.setDescription(procDTO.getDescription());
                    process.setRecipe(existingRecipe);
                    existingRecipe.getProcess().add(process);
                }
            }

            // ✅ Update last modified date
            existingRecipe.setLastUpdatedDate(LocalDate.now());

            // ✅ Save everything
            recipeRepository.save(existingRecipe);

        } catch (IOException e) {
            throw new RuntimeException("Error saving recipe image", e);
        }
    }


    @Override
    public List<Recipe> findByFilters(List<String> mealTypes, List<String> cuisineTypes) {
        if (!mealTypes.isEmpty() && !cuisineTypes.isEmpty()) {
            return recipeRepository.findByMealTypesAndCuisineTypes(mealTypes, cuisineTypes);
        } else if (!mealTypes.isEmpty()) {
            return recipeRepository.findByMealTypes(mealTypes);
        } else if (!cuisineTypes.isEmpty()) {
            return recipeRepository.findByCuisineTypes(cuisineTypes);
        } else {
            return recipeRepository.getAllRecipes();
        }
    }
}