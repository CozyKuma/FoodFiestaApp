package com.example.cozykuma.foodinventory;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;


public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
         PopulateDbAsync task = new PopulateDbAsync(db);
         task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static FoodItem addFoodItem(final AppDatabase db, FoodItem foodItem) {
        db.foodItemDao().insertAll();
        return foodItem;
    }

    private static void populateWithTestData(AppDatabase db) {
        FoodItem foodItem = new FoodItem();
        foodItem.setItemName("Milk");
        foodItem.setCategory(FoodCategory.getCategoryList().get(1));
        foodItem.setDateExpire("14-05-2018");
        addFoodItem(db, foodItem);

        List<FoodItem> foodItemList = db.foodItemDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + foodItemList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }
    }
}


