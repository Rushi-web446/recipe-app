package com.example.recipeapp.repository.recipe.impl;

import com.example.recipeapp.model.*;
import com.example.recipeapp.repository.recipe.RecipeCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class RecipeCustomRepositoryImpl implements RecipeCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> getAllRecipes() {
        String jpql = "SELECT r FROM Recipe r";
        return entityManager.createQuery(jpql, Recipe.class).getResultList();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        String jpql = "SELECT r FROM Recipe r WHERE r.id = :recipeId";
        try {
            return entityManager
                    .createQuery(jpql, Recipe.class)
                    .setParameter("recipeId", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // or throw a custom exception
        }

    }

    @Override
    @Transactional
    public void save(Recipe recipe, Chef chef) {


        System.out.println("\n\n %%% ########  bharat mata ki jai  : " + chef.getId() + "     ::: " + recipe.getId() + "\n\n");



        // Persist recipe first — to generate ID
        entityManager.persist(recipe);
        entityManager.flush(); // force insert, now recipe.getId() is non-null

        // Save NeededThings
        if (recipe.getNeededThings() != null) {
            for (NeededThing thing : recipe.getNeededThings()) {
                thing.setRecipe(recipe);
                entityManager.persist(thing);
            }
        }

        // Save Nutrition
        if (recipe.getNutrition() != null) {
            for (Nutrition nutrition : recipe.getNutrition()) {
                nutrition.setRecipe(recipe);
                entityManager.persist(nutrition);
            }
        }

        // Save Process
        if (recipe.getProcess() != null) {
            for (RecipeProcess step : recipe.getProcess()) {
                step.setRecipe(recipe);
                entityManager.persist(step);
            }
        }
        System.out.println("\n\n %%%   bharat mata ki jai  : " + chef.getId() + "     ::: " + recipe.getId() + "\n\n");

        entityManager.persist(recipe);
        entityManager.flush(); // force all inserts


        // ⚠️ Manually insert into join table now — recipe ID exists
        entityManager.createNativeQuery("INSERT INTO chef_uploaded_recipe (chef_id, recipe_id) VALUES (:chefId, :recipeId)")
                .setParameter("chefId", chef.getId())
                .setParameter("recipeId", recipe.getId())
                .executeUpdate();
    }

    @Override
    public List<Recipe> findByFilters(List<String> mealTypeList, List<String> cuisineTypeList) {
        return List.of();
    }


//    @Override
//    @Transactional
//    public void update(Recipe updatedRecipe) {
//        Recipe existingRecipe = entityManager.find(Recipe.class, updatedRecipe.getId());
//
//        if (existingRecipe == null) {
//            throw new EntityNotFoundException("Recipe not found with ID: " + updatedRecipe.getId());
//        }
//
//        // Update scalar fields
//        existingRecipe.setName(updatedRecipe.getName());
//        existingRecipe.setNameDescription(updatedRecipe.getNameDescription());
//        existingRecipe.setMealType(updatedRecipe.getMealType());
//        existingRecipe.setCuisineType(updatedRecipe.getCuisineType());
//        existingRecipe.setTimeTakeForMade(updatedRecipe.getTimeTakeForMade());
//        existingRecipe.setRecipeImage(updatedRecipe.getRecipeImage());
//        // ... other fields
//
//        // Remove and re-add NeededThings
//        existingRecipe.getNeededThings().clear();
//        for (NeededThing nt : updatedRecipe.getNeededThings()) {
//            nt.setRecipe(existingRecipe);
//            existingRecipe.getNeededThings().add(nt);
//        }
//
//        // Remove and re-add Nutrition
//        existingRecipe.getNutrition().clear();
//        for (Nutrition n : updatedRecipe.getNutrition()) {
//            n.setRecipe(existingRecipe);
//            existingRecipe.getNutrition().add(n);
//        }
//
//        // Remove and re-add Process
//        existingRecipe.getProcess().clear();
//        for (RecipeProcess p : updatedRecipe.getProcess()) {
//            p.setRecipe(existingRecipe);
//            existingRecipe.getProcess().add(p);
//        }
//
//        entityManager.merge(existingRecipe);
//    }

}