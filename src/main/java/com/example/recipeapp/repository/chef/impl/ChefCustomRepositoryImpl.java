package com.example.recipeapp.repository.chef.impl;

import com.example.recipeapp.model.Chef;
import com.example.recipeapp.model.User;
import com.example.recipeapp.repository.chef.ChefCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChefCustomRepositoryImpl implements ChefCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public Chef getChefByChefId(Long chefId) {
        String jpql = "SELECT c FROM Chef c WHERE c.id = :chefId";
        try {
            return entityManager
                    .createQuery(jpql, Chef.class)
                    .setParameter("chefId", chefId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // or throw a custom exception
        }
    }


    @Override
    @Transactional
    public String deleteUploadedRecipe(Chef chef, Long recipeId) {

        // Authorization check
        String checkSql = "SELECT COUNT(*) FROM chef_uploaded_recipe WHERE recipe_id = :recipeId AND chef_id = :chefId";
        Long count = ((Number) entityManager.createNativeQuery(checkSql)
                .setParameter("recipeId", recipeId)
                .setParameter("chefId", chef.getId())
                .getSingleResult()).longValue();

        if (count == 0) {
            throw new AccessDeniedException("You are not authorized to delete this recipe.");
        }

        // Delete from reference tables
        String[] tables = {
                "user_saved_recipes",
                "needed_things",
                "nutrition",
                "recipe_process",
                "chef_uploaded_recipe"
        };

        for (String table : tables) {
            String sql = "DELETE FROM " + table + " WHERE recipe_id = :recipeId";
            entityManager.createNativeQuery(sql)
                    .setParameter("recipeId", recipeId)
                    .executeUpdate();
        }

        // Finally delete from recipes
        String sql = "DELETE FROM recipes WHERE id = :recipeId";
        entityManager.createNativeQuery(sql)
                .setParameter("recipeId", recipeId)
                .executeUpdate();

//        log.info("Successfully deleted recipe with ID {}", recipeId);
        System.out.println("\n\n\nhehehehehehehehehe\n\n\n");
        return "fg";
    }
}