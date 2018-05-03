package com.example.cozykuma.foodinventory;


import java.util.ArrayList;

/**
 * Created by CozyKuma on 04-04-2018.
 */

public class FoodCategory {

    private String categoryName;
    private int id;
    private String imgURL;
    private int datePreset;
    private static ArrayList<FoodCategory> foodCategories = new ArrayList<>();

    public FoodCategory(String categoryName, int id, int datePreset, String image) {
        this.categoryName = categoryName;
        this.id = id;
        this.imgURL = image;
        this.datePreset = datePreset;
        foodCategories.add(this);
    }

    public FoodCategory(String categoryName, int id,  int datePreset) {
        this.categoryName = categoryName;
        this.id = id;
        this.datePreset = datePreset;
        foodCategories.add(this);
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static ArrayList<FoodCategory> getCategoryList() {
        return foodCategories;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return imgURL;
    }


    public void setImage(String image) {
        this.imgURL = image;
    }

    public int getDatePreset() {
        return datePreset;
    }

    public void setDatePreset(int datePreset) {
        this.datePreset = datePreset;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
