package com.example.recipeapp.dto;

public class NeededThingDto {
    private String itemName;
    private String quantity;

    // Getters and Setters

    public NeededThingDto() {
    }
    public NeededThingDto(String itemName, String quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
