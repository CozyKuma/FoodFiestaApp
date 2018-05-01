package com.example.cozykuma.foodinventory;

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

    public void changeCheckmark() {
        if(this.checked) {
            checked = false;
        } else {
            checked = true;
        }
    }

    public static void removeCheckedItemsFromList() {
        for(int i=0; i<shoppingList.size(); i++) {
            if(shoppingList.get(i).checked) {
                shoppingList.remove(i);
            }
        }
    }

    public static void addItemsToInventory() {
        for(int i=0; i<shoppingList.size(); i++) {
            if(shoppingList.get(i).checked) {
                FoodItem newFoodItem = new FoodItem(shoppingList.get(i).itemName, shoppingList.get(i).category);
            }
        }
    }
}
