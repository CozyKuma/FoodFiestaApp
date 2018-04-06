package com.example.cozykuma.foodinventory;

import android.media.Image;

import java.util.Date;

/**
 * Created by CozyKuma on 04-04-2018.
 */

public class FoodCategory {

    private String categoryName;
    private int id;
    private Image image;
    private Date datePreset;

    public FoodCategory(String categoryName, int id, Image image, Date datePreset) {
        this.categoryName = categoryName;
        this.id = id;
        this.image = image;
        this.datePreset = datePreset;
    }

    public String getCategoryName() {
        return categoryName;
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

    public Date getDatePreset() {
        return datePreset;
    }

    public void setDatePreset(Date datePreset) {
        this.datePreset = datePreset;
    }
}
