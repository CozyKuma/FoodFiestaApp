package com.example.cozykuma.foodinventory;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

// DATA ACCESS OBJECTS
@Dao
public interface FoodCategoryDao {
    @Query("SELECT * FROM foodCategories")
    List<FoodCategory> getAll();

    @Query("SELECT * FROM foodCategories WHERE id IN (:catIDs)")
    List<FoodCategory> loadAllByIds(int[] catIDs);

    @Query("SELECT * FROM foodCategories WHERE categoryName LIKE :first")
    FoodCategory findByName(String first);

    @Query("DELETE FROM foodCategories")
    void nukeTable();

    @Insert
    void insertOne(FoodCategory foodCategory);

    @Insert
    void insertAll(FoodCategory... foodCategories);

    @Insert
    void insertMultiple(ArrayList<FoodCategory> foodCategoryArrayList);

    @Update
    void update(FoodCategory foodCategory);

    @Update
    void updateAll(ArrayList<FoodCategory> foodCategoryArrayList);

    @Delete
    void delete(FoodCategory foodCategory);
}
