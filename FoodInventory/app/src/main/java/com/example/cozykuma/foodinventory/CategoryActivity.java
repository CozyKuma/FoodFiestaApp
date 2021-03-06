package com.example.cozykuma.foodinventory;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity {

    private ListView mListView;
    private List<FoodCategory> categoryList;
    private CategoryAdapter adapter;
    private FloatingActionButton mFab;
    private List<FoodCategory> catList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@android.support.annotation.NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentInv);
                    return true;
                case R.id.navigation_shoppinglist:
                    Intent intentShoppingList = new Intent(getApplicationContext(), ShoppingList.class);
                    startActivity(intentShoppingList);
                    return true;
                case R.id.navigation_settings:
                    Intent intentSettings = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intentSettings);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        android.support.design.widget.BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_shoppinglist);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton addCategorybtn = (FloatingActionButton) findViewById(R.id.addCategoryButton);
        addCategorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        mListView = (ListView) findViewById(R.id.categorylistview);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detailsIntent = new Intent(view.getContext(),CategoryDetails.class);
                detailsIntent.putExtra("Position",i);
                startActivity(detailsIntent);
            }
        });

        Thread getCategories = new Thread(new Runnable() {
            @Override
            public void run() {
                catList = MainActivity.appDatabase.foodCategoryDao().getAll();
            }
        });
        getCategories.start();

        try {
            getCategories.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupt Occurred");
            e.printStackTrace();
        }

        ArrayList<FoodCategory> arrCatList = new ArrayList<>();

        if(!catList.isEmpty()) {
            for (int i = 0; i < catList.size(); i++) {
                arrCatList.add(catList.get(i));
            }
        }


        adapter = new CategoryAdapter(this, R.layout.simple_category_item1, arrCatList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detailsIntent = new Intent(view.getContext(),CategoryDetails.class);
                detailsIntent.putExtra("Position",i);
                startActivity(detailsIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
