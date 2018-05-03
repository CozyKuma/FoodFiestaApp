package com.example.cozykuma.foodinventory;

import android.arch.persistence.room.TypeConverter;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CategoryTypeConverter implements Serializable {

    Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();

        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();

        String json = gson.toJson(list);

        return json;
    }
}
