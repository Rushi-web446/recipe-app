package com.example.recipeapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chefs")
public class Chef {

    @Id
    private Long id;

    private String experience;

    @Column(columnDefinition = "TEXT")
    private String aboutChef;

    private String chefImage;

    public String getChefImage() {
        return chefImage;
    }

    public void setChefImage(String chefImage) {
        this.chefImage = chefImage;
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;


    @ManyToMany
    @JoinTable(
            name = "chef_uploaded_recipe",
            joinColumns = @JoinColumn(name = "chef_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> uploadedRecipes;


    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAboutChef() {
        return aboutChef;
    }

    public void setAboutChef(String aboutChef) {
        this.aboutChef = aboutChef;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Recipe> getUploadedRecipes() {
        return uploadedRecipes;
    }

    public void setUploadedRecipes(List<Recipe> uploadedRecipes) {
        this.uploadedRecipes = uploadedRecipes;
    }

    public Chef() {
        this.uploadedRecipes = new ArrayList<>();
    }
    public Chef(Long id, String experience, String aboutChef, User user, String chefImage) {
        this.id = id;
        this.experience = experience;
        this.aboutChef = aboutChef;
        this.user = user;
        this.chefImage = chefImage;

    }

    @Override
    public String toString() {
        return "Chef{" +
                "experience=" + experience +
                ", aboutChef='" + aboutChef + '\'' +
                '}';
    }
}