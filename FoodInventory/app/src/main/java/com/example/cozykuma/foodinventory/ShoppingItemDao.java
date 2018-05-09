package com.example.cozykuma.foodinventory;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ShoppingItemDao {

    @Query("SELECT * FROM shoppingItems")
    List<ShoppingItem> getAll();

    @Query("SELECT * FROM shoppingItems WHERE itemId IN (:itemIDs)")
    List<ShoppingItem> loadAllByIds(int[] itemIDs);

    @Query("SELECT * FROM shoppingItems WHERE item_name LIKE :first")
    ShoppingItem findByName(String first);

    @Query("DELETE FROM shoppingItems")
    void nukeTable();

    @Insert
    void insertOne(ShoppingItem shoppingItem);

    @Insert
    void insertAll(ShoppingItem... shoppingItems);

    @Insert
    void insertMultiple(ArrayList<ShoppingItem> shoppingItemArrayList);

    @Update
    void update(ShoppingItem shoppingItem);

    @Update
    void updateAll(ArrayList<ShoppingItem> shoppingItemArrayList);

    @Delete
    void delete(ShoppingItem shoppingItem);

    @Delete
    void deleteMultiple(ArrayList<ShoppingItem> shoppingItemArrayList);

}
