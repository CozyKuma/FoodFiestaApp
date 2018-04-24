package com.example.cozykuma.foodinventory;

import android.media.Image;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by CozyKuma on 04-04-2018.
 */

public class FoodCategory {

    private String categoryName;
    private int id;
    private Image image;
    private int datePreset;
    private static ArrayList<FoodCategory> foodCategories = new ArrayList<>();

    public FoodCategory(String categoryName, int id, Image image, int datePreset) {
        this.categoryName = categoryName;
        this.id = id;
        this.image = image;
        this.datePreset = datePreset;
        foodCategories.add(this);
    }

    public FoodCategory(String categoryName, int id,  int datePreset) {
        this.categoryName = categoryName;
        this.id = id;
        this.datePreset = datePreset;
        foodCategories.add(this);
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
