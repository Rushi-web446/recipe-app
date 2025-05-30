package com.example.recipeapp.repository.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.recipeapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.multipart.MultipartFile;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.userImage = :image, u.aboutUser = :about WHERE u.id = :userId")
    void updateProfile(@Param("userId") Long userId,
                       @Param("image") MultipartFile image,
                           @Param("about") String about);

}
