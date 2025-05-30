package com.example.recipeapp.controller.chef;

import com.example.recipeapp.dto.RecipeUploadDto;
import com.example.recipeapp.model.*;
import com.example.recipeapp.service.chef.ChefService;
import com.example.recipeapp.service.recipe.RecipeService;
import com.example.recipeapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping("/chef")
public class ChefController {

    @Autowired
    UserService userService;

    @Autowired
    ChefService chefService;

    @Autowired
    RecipeService recipeService;



    @GetMapping("/home")
    public String loadChefHomePage(Model model, Principal principal) {
        System.out.println("\n\n\n  hehehehehehehhehehehehehehehehehehehehe    \n\n\n\n\n");
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            // handle case where user is not found
            return "redirect:/login?error";
        }

        Chef chef = chefService.getChefByChefId(user.getId());

        model.addAttribute("userDetails", user);
        model.addAttribute("chefDetails", chef); // can be null, Thymeleaf will handle


        return "chef/chef-home";
    }

    @GetMapping("/upload-recipe")
    public String showUploadRecipePage() {
        return "recipe/upload-recipe";
    }

    @PostMapping("/upload-recipe")
    public String uploadRecipe(@ModelAttribute RecipeUploadDto recipeDto, Principal principal) {

        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        Chef chef = chefService.getChefByChefId(user.getId());

        recipeService.saveRecipeFromDto(recipeDto, chef);

        return "redirect:/chef/home";

    }


    @DeleteMapping("/delete-recipe/{recipeId}")
    public String deleteUploadedRecipe(@PathVariable Long recipeId, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        Chef chef = chefService.getChefByChefId(user.getId());


        chefService.deleteUploadedRecipe(chef, recipeId);
        return "redirect:/chef/home";
    }


    @GetMapping("/update-recipe/{recipeId}")
    public String showUpdateRecipePage(@PathVariable Long recipeId, Model model) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("mealTypes", MealType.values());
        model.addAttribute("cuisineTypes", CuisineType.values());

        return "chef/update-recipe";


//        RecipeUploadDto recipeDto = recipeService.getRecipeDtoByRecipeId(recipeId);
//        model.addAttribute("recipeDto", recipeDto);
//        return "recipe/update-recipe";
    }


    @PostMapping("/update-recipe/{recipeId}")
    public String updateUploadedRecipe(@ModelAttribute RecipeUploadDto recipeDto,@PathVariable Long recipeId, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        Chef chef = chefService.getChefByChefId(user.getId());


        recipeService.updateUploadedRecipe(recipeDto, recipeId, chef);


        return "redirect:/chef/home";
    }


    @PostMapping("/update-profile")
    public String updateChefProfile(@RequestParam("imageFile") MultipartFile imageFile,
                                @RequestParam("aboutChef") String aboutChef,
                                    @RequestParam("experience") String experience,
                                    Principal principal) {
        // Lookup user via principal.getName()
        // Save imageFile to disk and get path
        // Save aboutUser and image path to user entity

        System.out.println("\n\n\n\n\n aavne baap aavv \n\n\n\n");

        String email = principal.getName();


        User user = userService.getUserByEmail(email);
        Chef chef = chefService.getChefByChefId(user.getId());



        chefService.updateChefProfile(chef.getId(), imageFile, aboutChef, experience);
        return "redirect:/chef/home";
    }



}