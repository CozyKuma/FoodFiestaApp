package com.example.cozykuma.foodinventory;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Serializable {

    //private static final String TAG = "MainActivity";
    private TextView mTextMessage;
    private ListView mListView;
    private FloatingActionButton mFab;
    private List<FoodItem> foodList;
    private List<FoodCategory> categoryList;
    private static boolean isFinished = false;
    private FoodListAdapter adapter;
    static final String DATABASE_NAME = "FoodItemDatabase";
    public static AppDatabase appDatabase;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Database //
        appDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase.class)
                .build();
        // End Data //

        //Log.d(TAG, "onCreate: " + "Started.");
        mTextMessage = (TextView) findViewById(R.id.message);
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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detailsIntent = new Intent(view.getContext(),ItemDetails.class);
                detailsIntent.putExtra("Position",i);
                startActivity(detailsIntent);
            }
        });

        Thread loadDB = new Thread(new Runnable() {
            @Override
            public void run() {
                List<FoodItem> itemList = AppDatabase.getAppDatabase(getApplicationContext()).foodItemDao().getAll();
                ArrayList<FoodItem> itemArrayList = new ArrayList<>(itemList.size());
                itemArrayList.addAll(itemList);
                FoodItem.setListOfItems(itemArrayList);
                }
        });

        loadDB.start();

        try {
            loadDB.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interrupt Ocurred");
            e.printStackTrace();
        }


        adapter = new FoodListAdapter(this, R.layout.simple_food_item1, FoodItem.sortList(FoodItem.getSortType(), FoodItem.getListOfItems()));
        mListView.setAdapter(adapter);
        }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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