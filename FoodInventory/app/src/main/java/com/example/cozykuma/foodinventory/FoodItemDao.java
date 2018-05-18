package com.example.cozykuma.foodinventory;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


// DATA ACCESS OBJECTS
@Dao
public interface FoodItemDao {
    @Query("SELECT * FROM foodItems")
    List<FoodItem> getAll();

    @Query("SELECT * FROM foodItems WHERE itemId IN (:itemIDs)")
    List<FoodItem> loadAllByIds(int[] itemIDs);

    @Query("SELECT * FROM foodItems WHERE item_name LIKE :first")
    FoodItem findByName(String first);

    @Query("SELECT * FROM foodItems WHERE categoryName IN (:category)")
    List<FoodItem> findByCategoryName(String category);

    @Query("DELETE FROM foodItems")
    void nukeTable();

    @Insert
    void insertOne(FoodItem foodItem);

    @Insert
    void insertAll(FoodItem... foodItems);

    @Insert
    void insertMultiple(ArrayList<FoodItem> foodItemArrayList);

    @Update
    void update(FoodItem foodItem);

    @Update
    void updateAll(ArrayList<FoodItem> foodItemArrayList);

    @Delete
    void delete(FoodItem foodItem);

    @Delete
    void deleteMultiple(ArrayList<FoodItem> foodItemArrayList);
}
