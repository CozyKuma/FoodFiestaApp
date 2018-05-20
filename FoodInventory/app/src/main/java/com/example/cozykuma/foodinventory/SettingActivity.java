package com.example.cozykuma.foodinventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private ExpandableListAdapter adapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

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
        setContentView(R.layout.activity_setting);

        setTitle("Settings");

        android.support.design.widget.BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_shoppinglist);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listView = (ExpandableListView)findViewById(R.id.settinglist_view);
        initData();
        adapter = new SettingExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(adapter);


        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d(""+groupPosition,"Pos");
                return false;
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                switch (groupPosition){
                    case 0:
                        switch (childPosition){
                            case 0:
                               Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
                               startActivity(intent);
                               break;
                            case 1:
                                AlertDialog.Builder adbRemove = new AlertDialog.Builder(SettingActivity.this);
                                adbRemove.setIcon(R.drawable.ic_remove_black_24dp); //  <---- PLACEHOLDER(?)
                                adbRemove.setTitle("Clear Inventory?");
                                adbRemove.setMessage("Are you sure you want to clear the inventory?");
                                adbRemove.setNegativeButton("Cancel",null);
                                adbRemove.setPositiveButton("Clear", new AlertDialog.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Thread clearInv = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                clearInventory();
                                            }
                                        });
                                        clearInv.start();

                                        try {
                                            clearInv.join();
                                        } catch (InterruptedException e) {
                                            System.out.println("Interrupt Occurred");
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), "Inventory has been cleared.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                adbRemove.show();
                                break;

                        }
                        break;
                    case 1:
                        switch (childPosition){
                            case 0:
                                break;
                        }
                        break;
                }
                Log.d(""+childPosition,"PosChild");
                return false;
            }
        });


    }
    private void initData(){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("General");
        listDataHeader.add("Notifications (WIP)");

        List<String> categoryH = new ArrayList<>();
        categoryH.add("Manage Categories");
        categoryH.add("Clear Inventory");
        List<String> notifyH = new ArrayList<>();
        notifyH.add("WIP");

        listHash.put(listDataHeader.get(0),categoryH);
        listHash.put(listDataHeader.get(1),notifyH);
    }

    private void clearInventory() {
        MainActivity.appDatabase.foodItemDao().nukeTable();
    }
}
