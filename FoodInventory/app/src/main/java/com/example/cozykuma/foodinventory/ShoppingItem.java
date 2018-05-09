package com.example.cozykuma.foodinventory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShoppingItem {

    private String itemName;
    private static int countId = 0;
    private int itemId;
    private FoodCategory category;
    private static ArrayList<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
    private boolean checked;

    ShoppingItem(String itemName, FoodCategory category) {
        this.itemName = itemName;
        this.category = category;
        this.itemId = countId;
        countId++;
        this.checked = true;
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

    public boolean isChecked() {
        return checked;
    }

    public static void removeCheckedItemsFromList() {
        int size = shoppingList.size()-1;
        for(int i= size; i>= 0; i--) {
            {
                shoppingList.remove(i);
            }

        }
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
