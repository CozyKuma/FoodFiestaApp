package com.example.cozykuma.foodinventory;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(entities = {FoodItem.class, FoodCategory.class, ShoppingItem.class}, version = 2)
@TypeConverters({DateTypeConverter.class, CategoryTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract FoodItemDao foodItemDao();
    public abstract FoodCategoryDao foodCategoryDao();
    public abstract ShoppingItemDao shoppingItemDao();

    public static AppDatabase getAppDatabase(Context context) {
        if(INSTANCE == null) {

            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "FoodItemDatabase")
                .addCallback(new Callback() {

                    // Runs when the database is created initially and pre-populates the FoodCategories with the "createDefaultCategories()-method".
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getAppDatabase(context).foodCategoryDao().insertAll(FoodCategory.createDefaultCategories());
                            }
                        });
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }
}