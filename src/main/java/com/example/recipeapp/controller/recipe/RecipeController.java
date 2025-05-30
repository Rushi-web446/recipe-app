package com.example.recipeapp.controller.recipe;

import com.example.recipeapp.model.Chef;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.User;
import com.example.recipeapp.service.chef.ChefService;
import com.example.recipeapp.service.recipe.RecipeService;
import com.example.recipeapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;
    @Autowired
    ChefService chefService;


    @GetMapping("/")
    public String loadHomePageWithAllRecipes(Model model) {
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        System.out.println("\n\n\n");

        for (Recipe recipe : allRecipes) {
            System.out.println(recipe.getRecipeImage());
        }
        System.out.println("\n\n\n");

        model.addAttribute("allRecipes", allRecipes);
        return "index";  // Looks for templates/index.html
    }


    @GetMapping("/recipes/{recipeId}")
    public  String loadRecipePage(@PathVariable Long recipeId, Model model) {

        Recipe recipe = recipeService.getRecipeById(recipeId);

        if (recipe == null) {
            return "recipe/recipe-not-found";
        }

        User user = userService.getChefUserByRecipeId(recipeId);
        Chef chef = chefService.getChefByChefId(user.getId());

        model.addAttribute("userDetails", user);
        model.addAttribute("chefDetails", chef); // can be null, Thymeleaf will handle
        model.addAttribute("recipe", recipe);
        return "recipe/recipe";
    }


    @GetMapping("/recipes")
    public String getFilteredRecipes(
            @RequestParam(required = false) String mealTypes,
            @RequestParam(required = false) String cuisineTypes,
            Model model) {

        // Convert comma-separated values to lists
        List<String> mealTypeList = mealTypes != null ?
                Arrays.asList(mealTypes.split(",")) : Collections.emptyList();

        List<String> cuisineTypeList = cuisineTypes != null ?
                Arrays.asList(cuisineTypes.split(",")) : Collections.emptyList();

        // Fetch filtered recipes from service
        List<Recipe> recipes = recipeService.findByFilters(mealTypeList, cuisineTypeList);
        model.addAttribute("allRecipes", recipes);
        model.addAttribute("hasFilters", true);
//        model.addAttribute("activeMealTypes", mealTypeList);
//        model.addAttribute("activeCuisineTypes", cuisineTypeList);
//        model.addAttribute("hasFilters", !mealTypeList.isEmpty() || !cuisineTypeList.isEmpty());


        for (Recipe recipe : recipes) {
            System.out.println("vdfvfv" + recipe);
        }

        return "index";
    }


}