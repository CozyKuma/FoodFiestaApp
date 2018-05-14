package com.example.cozykuma.foodinventory;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Entity(tableName = "shoppingItems")
public class ShoppingItem {

    @ColumnInfo(name = "item_name")
    private String itemName;

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    @Embedded
    private FoodCategory category;

    private static ArrayList<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
    private boolean checked;

    ShoppingItem() {
        this.category = FoodCategory.getCategoryList().get(0);
        shoppingList.add(this);
    }

    @Ignore
    ShoppingItem(String itemName, FoodCategory category) {
        this.itemName = itemName;
        this.category = category;
        this.checked = false;
        shoppingList.add(this);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setChecked(boolean checked){this.checked = checked; }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public static ArrayList<ShoppingItem> getShoppingList() {
        return shoppingList;
    }

    public static void setShoppingList(ArrayList<ShoppingItem> list) {
        shoppingList = list;
    }

    public boolean isChecked() {
        return checked;
    }

    public static ArrayList<ShoppingItem> removeCheckedItemsFromList() {
        ArrayList<ShoppingItem> removeItemsList = new ArrayList<>();
        int size = shoppingList.size()-1;
        for(int i= size; i>= 0; i--) {
            {
                if (shoppingList.get(i).checked) {
                    removeItemsList.add(shoppingList.get(i));
                    shoppingList.remove(i);
                }
            }
        }
        return removeItemsList;
    }

    public static ArrayList<FoodItem> addItemsToInventory() {
        ArrayList<FoodItem> newItemList = new ArrayList<>();
        for(int i=0; i<shoppingList.size(); i++) {
            if(shoppingList.get(i).checked) {
                FoodItem newFoodItem = new FoodItem(shoppingList.get(i).itemName, shoppingList.get(i).category);
                newItemList.add(newFoodItem);
            }
        }

        int siz = shoppingList.size()-1;
        for(int i = siz; i >=0; i-- ) {
            if (shoppingList.get(i).checked) {
                shoppingList.remove(i);
            }
        }

        return newItemList;
    }
}