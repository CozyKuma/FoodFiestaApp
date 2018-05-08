package com.example.cozykuma.foodinventory;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {FoodItem.class, FoodCategory.class}, version = 2)
@TypeConverters({DateTypeConverter.class, CategoryTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract FoodItemDao foodItemDao();
    public abstract FoodCategoryDao foodCategoryDao();

    public static AppDatabase getAppDatabase(Context context) {
        if(INSTANCE == null) {

            INSTANCE =

            Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "FoodItemDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}