package com.example.cozykuma.foodinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG = "MainActivity";
    private TextView mTextMessage;
    private ListView mListView;
    //private ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
    private ArrayList<FoodCategory> categoryList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    Intent intentDash = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intentDash);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentInv);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.d(TAG, "onCreate: " + "Started.");

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });


        mListView = (ListView) findViewById(R.id.foodlistview);

        // Create Food Items for test

        /*FoodItem milk = new FoodItem("Milk", "12-04-2018");
        FoodItem meat = new FoodItem("Meat", "14-04-2018");
        FoodItem yoghurt = new FoodItem("Yoghurt", "07-04-2018");
        FoodItem pickles = new FoodItem("Pickles", "12-07-2018");
        FoodItem cheese = new FoodItem("Cheese", "17-05-2018");
        FoodItem juice = new FoodItem("Apple Juice", "22-04-2018");
        FoodItem eggs = new FoodItem("Eggs", "06-06-2018");*/

        FoodListAdapter adapter = new FoodListAdapter(this, R.layout.simple_food_item1, FoodItem.sortList(FoodItem.sortTypes.DAYSLEFT, FoodItem.getListOfItems()));
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
