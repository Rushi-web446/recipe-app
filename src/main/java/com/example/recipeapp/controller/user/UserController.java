package com.example.recipeapp.controller.user;


import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.User;
import com.example.recipeapp.security.CustomUserDetails;
import com.example.recipeapp.service.recipe.RecipeService;
import com.example.recipeapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private RecipeService recipeService;


    @GetMapping("/home")
    public String loadUserHomePage(Model model, Principal principal) {

        // getting email of current loggedIn user.
        String email = principal.getName();

        User user = userService.getUserByEmail(email);


        model.addAttribute("userDetails", user);

        return "user/user-home";
    }


    @PostMapping("/save-recipe/{recipeId}")
    public String saveRecipeToUser(@PathVariable Long recipeId, Principal principal, RedirectAttributes redirectAttributes) {


        String email = principal.getName();


        User user = userService.getUserByEmail(email);


        String message = userService.saveRecipeToUser(user.getId(), recipeId);
        redirectAttributes.addFlashAttribute("saveMessage", message);

        return "redirect:/user/home";
    }



    @DeleteMapping("/delete-recipe/{recipeId}")
    public String deleteRecipeToUser(@PathVariable Long recipeId, Principal principal) {


        String email = principal.getName();


        User user = userService.getUserByEmail(email);


        userService.deleteRecipeToUser(user.getId(), recipeId);
        System.out.println("\n\n\n aaavii gaaayaaaa " + user + "   \n\n\n");
        return "redirect:/user/home";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam("imageFile") MultipartFile imageFile,
                                @RequestParam("aboutUser") String aboutUser,
                                Principal principal) {
        // Lookup user via principal.getName()
        // Save imageFile to disk and get path
        // Save aboutUser and image path to user entity

        System.out.println("\n\n\n\n\n aavne baap aavv \n\n\n\n");

        String email = principal.getName();


        User user = userService.getUserByEmail(email);


        userService.updateProfile(user.getId(), imageFile, aboutUser);
        return "redirect:/user/home";
    }


//    @GetMapping("/home")
//    public String getFilteredRecipes(
//            @RequestParam(required = false) String mealTypes,
//            @RequestParam(required = false) String cuisineTypes,
//            Model model) {
//
//        // Convert comma-separated values to lists
//        List<String> mealTypeList = mealTypes != null ?
//                Arrays.asList(mealTypes.split(",")) : Collections.emptyList();
//
//        List<String> cuisineTypeList = cuisineTypes != null ?
//                Arrays.asList(cuisineTypes.split(",")) : Collections.emptyList();
//
//        // Fetch filtered recipes from service
//        List<Recipe> recipes = recipeService.findByFilters(mealTypeList, cuisineTypeList);
//        model.addAttribute("allRecipes", recipes);
//        model.addAttribute("hasFilters", true);
////        model.addAttribute("activeMealTypes", mealTypeList);
////        model.addAttribute("activeCuisineTypes", cuisineTypeList);
////        model.addAttribute("hasFilters", !mealTypeList.isEmpty() || !cuisineTypeList.isEmpty());
//
//
//        for (Recipe recipe : recipes) {
//            System.out.println("vdfvfv" + recipe);
//        }
//
//        return "user/home";
//    }


}