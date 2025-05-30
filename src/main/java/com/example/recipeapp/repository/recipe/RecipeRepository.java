package com.example.recipeapp.repository.recipe;

import com.example.recipeapp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeCustomRepository {


    // New methods for filtering
    @Query("SELECT r FROM Recipe r WHERE r.mealType IN :mealTypes")
    List<Recipe> findByMealTypes(@Param("mealTypes") List<String> mealTypes);

    @Query("SELECT r FROM Recipe r WHERE r.cuisineType IN :cuisineTypes")
    List<Recipe> findByCuisineTypes(@Param("cuisineTypes") List<String> cuisineTypes);

    @Query("SELECT r FROM Recipe r WHERE r.mealType IN :mealTypes AND r.cuisineType IN :cuisineTypes")
    List<Recipe> findByMealTypesAndCuisineTypes(
            @Param("mealTypes") List<String> mealTypes,
            @Param("cuisineTypes") List<String> cuisineTypes);

}
