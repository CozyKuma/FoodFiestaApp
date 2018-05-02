package com.example.cozykuma.foodinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private static boolean isFinished = false;
    private FoodListAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    //mTextMessage.setText(R.string.title_home);
                    Intent intentDash = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentDash);
                    return true;
                case R.id.navigation_shoppinglist:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentInv);
                    return true;
                case R.id.navigation_settings:
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

        Button sort = (Button) findViewById(R.id.btn_sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogbox_sort_select, null);
                Button sortName = (Button) mView.findViewById(R.id.btn_sort_1);
                Button sortCategory = (Button) mView.findViewById(R.id.btn_sort_2);
                Button sortDaysLeft = (Button) mView.findViewById(R.id.btn_sort_3);
                Button sortDateAdded = (Button) mView.findViewById(R.id.btn_sort_4);
                Button sortProgress = (Button) mView.findViewById(R.id.btn_sort_5);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                sortName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FoodItem.setSortType(FoodItem.sortTypes.NAME);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                sortCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FoodItem.setSortType(FoodItem.sortTypes.CATEGORY);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                sortDaysLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FoodItem.setSortType(FoodItem.sortTypes.DAYSLEFT);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                sortDateAdded.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FoodItem.setSortType(FoodItem.sortTypes.DATEADDED);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                sortProgress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FoodItem.setSortType(FoodItem.sortTypes.PROGRESS);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

            }
        });

        if(!isFinished) {
            createDefaultCategories();
            isFinished = true;
        }


        mListView = (ListView) findViewById(R.id.foodlistview);

        adapter = new FoodListAdapter(this, R.layout.simple_food_item1, FoodItem.sortList(FoodItem.getSortType(), FoodItem.getListOfItems()));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            isFinished = false;
        }
    }

    protected void createDefaultCategories() {
        FoodCategory milk = new FoodCategory("Milk", 1, 7);
        FoodCategory meat = new FoodCategory("Meat", 2, 7);
        FoodCategory vegetable = new FoodCategory("Vegetables", 3, 10);
        FoodCategory fruit = new FoodCategory("Fruit", 4, 14);
    }
}