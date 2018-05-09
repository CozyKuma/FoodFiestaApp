package com.example.cozykuma.foodinventory;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by CozyKuma on 04-04-2018.
 */

@Entity(tableName = "foodCategories")
public class FoodCategory {

    private String categoryName;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imgURL;
    private int datePreset;
    private static ArrayList<FoodCategory> foodCategories = new ArrayList<>();

    public FoodCategory(String categoryName, int datePreset, String image) {
        this.categoryName = categoryName;
        this.imgURL = image;
        this.datePreset = datePreset;
        foodCategories.add(this);
    }

    public FoodCategory(String categoryName, int datePreset) {
        this.categoryName = categoryName;
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

    public static void setCategoryList(ArrayList<FoodCategory> listOfCategoryList) { FoodCategory.foodCategories =  listOfCategoryList; }

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

    public static FoodCategory[] createDefaultCategories() {
        return new FoodCategory[]{
                new FoodCategory("Default", 0),
                new FoodCategory("Milk", 7, "drawable://" + R.drawable.milk128px),
                new FoodCategory("Diary", 14),
                new FoodCategory("Meat", 7, "drawable://" + R.drawable.meat128px),
                new FoodCategory("Vegetables", 10, "drawable://" + R.drawable.vegetables128px),
                new FoodCategory("Fruit", 14, "drawable://" + R.drawable.fruit128px),
                new FoodCategory("Fish", 7)
        };
    }
}
