package com.example.recipeapp.repository.chef;

import com.example.recipeapp.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ChefRepository extends JpaRepository<Chef, Long>, ChefCustomRepository {
    @Modifying
    @Transactional
    @Query("UPDATE Chef u SET u.chefImage = :image, u.aboutChef = :about, u.experience = :experience WHERE u.id = :chefId")
    void updateChefProfile(@Param("userId") Long chefId,
                       @Param("image") MultipartFile image,
                       @Param("about") String about,
                        @Param("about") String experience);


}
