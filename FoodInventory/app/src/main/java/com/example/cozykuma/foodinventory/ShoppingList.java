package com.example.cozykuma.foodinventory;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingList extends AppCompatActivity{

    private ListView mListView;
    public ShoppingListAdapter adapter;

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
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        setTitle("Shopping List");

        android.support.design.widget.BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_shoppinglist);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton floatyBoii = (FloatingActionButton) findViewById(R.id.floatingActionButton_shoppinglist);
        floatyBoii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddShoppingItem.class);
                startActivity(intent);
            }
        });

        Button mRemoveItem = (Button) findViewById(R.id.removeItemBtn);

        mRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<ShoppingItem> removedItems = ShoppingItem.removeCheckedItemsFromList();

                Thread removeItemsThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.appDatabase.shoppingItemDao().deleteMultiple(removedItems);
                    }
                });
                removeItemsThread.start();

                try {
                    removeItemsThread.join();
                } catch (InterruptedException e) {
                    System.out.println("Interrupt Occurred");
                    e.printStackTrace();
                }


                adapter.notifyDataSetChanged();
            }
        });

        Button mAddToInv = (Button) findViewById(R.id.addInvBtn);

        mAddToInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<FoodItem> newItemList = ShoppingItem.addItemsToInventory();

                Thread addItemsThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.appDatabase.foodItemDao().insertMultiple(newItemList);
                    }
                });

                addItemsThread.start();

                try {
                    addItemsThread.join();
                } catch (InterruptedException e) {
                    System.out.println("Interrupt Occurred");
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mListView = (ListView) findViewById(R.id.shoppinglist_view);

        Thread loadShoppingItems = new Thread(new Runnable() {
            @Override
            public void run() {
                // Food Items //
                List<ShoppingItem> itemList = MainActivity.appDatabase.shoppingItemDao().getAll();
                ArrayList<ShoppingItem> itemArrayList = new ArrayList<>(itemList.size());
                itemArrayList.addAll(itemList);
                ShoppingItem.setShoppingList(itemArrayList);

                MainActivity.appDatabase.shoppingItemDao().updateAll(ShoppingItem.getShoppingList());
            }
        });

        loadShoppingItems.start();

        try {
            loadShoppingItems.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interrupt Occurred");
            e.printStackTrace();
        }

        adapter = new ShoppingListAdapter(this, R.layout.simple_shopping_item1, ShoppingItem.getShoppingList());
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
    }

}
