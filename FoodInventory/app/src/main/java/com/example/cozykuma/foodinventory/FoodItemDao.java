package com.example.cozykuma.foodinventory;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FoodItemDao {
    @Query("SELECT * FROM fooditem")
    List<FoodItem> getAll();

    @Query("SELECT * FROM foodItem WHERE itemId IN (:itemIDs)")
    List<FoodItem> loadAllByIds(int[] itemIDs);

    @Query("SELECT * FROM fooditem WHERE item_name LIKE :first")
    FoodItem findByName(String first);

    @Insert
    void insertAll(FoodItem... foodItems);

    @Update
    void update(FoodItem foodItem);

    @Delete
    void delete(FoodItem foodItem);
}
