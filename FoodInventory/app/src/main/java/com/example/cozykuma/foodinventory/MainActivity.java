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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {

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

        FloatingActionButton floatyBoii = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatyBoii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        FoodCategory test1 = new FoodCategory("Milk", 1, 7);
        FoodCategory test2 = new FoodCategory("Meat", 2, 7);
        FoodCategory test3 = new FoodCategory("Vegetable", 3, 10);

        FoodItem item1 = new FoodItem("Item1", "24-04-2018");
        FoodItem item2 = new FoodItem("Item2", "24-04-2018");
        FoodItem item3 = new FoodItem("Item3", "24-04-2018");
        FoodItem item4 = new FoodItem("Item4", "24-04-2018");
        FoodItem item5 = new FoodItem("Item5", "24-04-2018");
        FoodItem item6 = new FoodItem("Item6", "24-04-2018");
        FoodItem item7 = new FoodItem("Item7", "24-04-2018");
        FoodItem item8 = new FoodItem("Item8", "24-04-2018");
        FoodItem item9 = new FoodItem("Item9", "24-04-2018");
        FoodItem item10 = new FoodItem("Item10", "24-04-2018");
        FoodItem item11 = new FoodItem("Item11", "24-04-2018");
        FoodItem item12 = new FoodItem("Item12", "24-04-2018");
        FoodItem item13 = new FoodItem("Item13", "24-04-2018");
        FoodItem item14 = new FoodItem("Item14", "24-04-2018");
        FoodItem item15 = new FoodItem("Item15","24-04-2018");

        mListView = (ListView) findViewById(R.id.foodlistview);


        FoodListAdapter adapter = new FoodListAdapter(this, R.layout.simple_food_item1, FoodItem.sortList(FoodItem.sortTypes.DAYSLEFT, FoodItem.getListOfItems()));
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detailsIntent = new Intent(view.getContext(),ItemDetails.class);
               // Bundle itemBundle = new Bundle();
                //itemBundle.putSerializable("List", FoodItem.getListOfItems());
               // detailsIntent.putExtra("List",FoodItem.getListOfItems());
                detailsIntent.putExtra("Position",i);
                startActivity(detailsIntent);
            }
        });
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
