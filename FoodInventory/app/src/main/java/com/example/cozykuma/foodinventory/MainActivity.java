package com.example.cozykuma.foodinventory;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
<<<<<<< HEAD
    private FloatingActionButton mFab;
    private List<FoodItem> foodList;
    private List<FoodCategory> categoryList;
=======
    private static boolean isFinished = false;
<<<<<<< HEAD
    public FoodListAdapter adapter;
>>>>>>> No-Branch
=======
    private FoodListAdapter adapter;
>>>>>>> Simon-branch

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
<<<<<<< HEAD
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    /*Intent intentDash = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intentDash);*/
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    /*Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentInv);*/
=======
                case R.id.navigation_list:
                    Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentInv);
>>>>>>> No-Branch
                    return true;
                case R.id.navigation_shoppinglist:
                    Intent intentShoppingList = new Intent(getApplicationContext(), ShoppingList.class);
                    startActivity(intentShoppingList);
                    return true;
                case R.id.navigation_settings:
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

<<<<<<< HEAD
        mTextMessage = (TextView) findViewById(R.id.message);
        /*BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener); */

        /*mFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(new View.OnClickListener() {
=======
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton floatyBoii = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatyBoii.setOnClickListener(new View.OnClickListener() {
>>>>>>> No-Branch
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
<<<<<<< HEAD
        }); */
=======
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
>>>>>>> No-Branch


        mListView = (ListView) findViewById(R.id.foodlistview);
        foodList = new ArrayList<FoodItem>();

<<<<<<< HEAD
        // Create Food Items for test

        FoodItem milk = new FoodItem("Milk", "12-04-2018");
        FoodItem meat = new FoodItem("Meat", "14-04-2018");
        FoodItem yoghurt = new FoodItem("Yoghurt", "12-04-2018");
        FoodItem pickles = new FoodItem("Pickles", "14-07-2018");
        FoodItem cheese = new FoodItem("Cheese", "17-05-2018");
        FoodItem juice = new FoodItem("Apple Juice", "22-04-2018");
        FoodItem eggs = new FoodItem("Eggs", "06-06-2018");

        //foodList = FoodItem.getListOfItems();
        foodList.add(milk);
        foodList.add(meat);
        foodList.add(yoghurt);
        foodList.add(pickles);
        foodList.add(cheese);
        foodList.add(juice);
        foodList.add(eggs);

        /* Collections.sort(foodList, new Comparator<FoodItem>() {
            @Override
            public int compare(FoodItem o1, FoodItem o2) {
                return o1.getDatesLeft() - o2.getDatesLeft();
            }
        }); */

<<<<<<< HEAD
        FoodListAdapter adapter = new FoodListAdapter(this, R.layout.simple_food_item1, foodList);
=======
        adapter = new FoodListAdapter(this, R.layout.simple_food_item1, FoodItem.sortList(FoodItem.getSortType(), FoodItem.getListOfItems()));
>>>>>>> No-Branch
        mListView.setAdapter(adapter);
=======
        adapter = new FoodListAdapter(this, R.layout.simple_food_item1, FoodItem.sortList(FoodItem.getSortType(), FoodItem.getListOfItems()));
        mListView.setAdapter(adapter);



>>>>>>> Simon-branch
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
        FoodCategory defaultCat = new FoodCategory("Default", 0, 0);
        FoodCategory milk = new FoodCategory("Milk", 1, 7, "drawable://" + R.drawable.milk128px);
        FoodCategory meat = new FoodCategory("Meat", 2, 7, "drawable://" + R.drawable.meat128px);
        FoodCategory vegetable = new FoodCategory("Vegetables", 3, 10, "drawable://" + R.drawable.vegetables128px);
        FoodCategory fruit = new FoodCategory("Fruit", 4, 14, "drawable://" + R.drawable.fruit128px);
    }
}