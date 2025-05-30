package com.example.recipeapp.repository.user.impl;

import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.User;
import com.example.recipeapp.repository.user.UserCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

    @Autowired
    EntityManager entityManager;



    @Override
    public User getUserByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        try {
            return entityManager
                    .createQuery(jpql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // or throw a custom exception
        }
    }

    @Override
    public List<Recipe> getSavedRecipesByUserId(Long userId) {
        String jpql = "SELECT r FROM User u JOIN u.savedRecipes r WHERE u.id = :userId";
        return entityManager.createQuery(jpql, Recipe.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    @Transactional // 🟢 ADD THIS
    public String saveRecipeToUser(Long userId, Long recipeId) {
        try {

            // Check if the recipe is already saved by this user
            String checkSql = "SELECT COUNT(*) FROM user_saved_recipes WHERE user_id = :user_id AND recipe_id = :recipe_id";

            Long count = ((Number) entityManager.createNativeQuery(checkSql)
                    .setParameter("user_id", userId)
                    .setParameter("recipe_id", recipeId)
                    .getSingleResult()).longValue();

            if (count > 0) {
                return "This recipe is already in your saved list!";
            }



            LocalDateTime savedAt = LocalDateTime.now();

            String sql = "INSERT INTO user_saved_recipes (user_id, recipe_id, saved_at) " +
                    "VALUES (:user_id, :recipe_id, :saved_at)";

            int result = entityManager.createNativeQuery(sql)
                    .setParameter("user_id", userId)
                    .setParameter("recipe_id", recipeId)
                    .setParameter("saved_at", savedAt)
                    .executeUpdate();

            System.out.println("Rows affected: " + result); // DEBUGGING
            return "Recipe saved successfully!";
        } catch (Exception e) {
            e.printStackTrace(); // Print full stack trace
            return "An error occurred while saving the recipe: " + e.getMessage();
        }
    }




    @Override
    @Transactional
    public String deleteRecipeToUser(Long userId, Long recipeId) {
        String sql = "DELETE FROM user_saved_recipes WHERE user_id = :userId AND recipe_id = :recipeId";

        int rowsAffected = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("recipeId", recipeId)
                .executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("✅ Recipe (ID: " + recipeId + ") removed from User (ID: " + userId + "). Rows affected: " + rowsAffected);
            return "Recipe deleted successfully!";
        } else {
            System.out.println("⚠️ No matching recipe found for deletion.");
            return "No recipe found for the given user and recipe IDs.";
        }
    }

    @Override
    public User getChefUserByRecipeId(Long recipeId) {
        String jpql = "SELECT c.user FROM Chef c JOIN c.uploadedRecipes r WHERE r.id = :recipeId";
        try {
            return entityManager
                    .createQuery(jpql, User.class)
                    .setParameter("recipeId", recipeId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // or handle with custom exception
        }
    }


//    @Modifying
//    @Query("UPDATE User u SET u.userImage = :image, u.aboutUser = :about WHERE u.id = :userId")
//    String updateProfile(@Param("userId") Long userId, @Param("image") MultipartFile image, @Param("about") String about) {
//        return "update.";
//    }

}
