package com.example.cozykuma.foodinventory;

import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.view.*;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class AddShoppingItem extends AppCompatActivity {

    private String itemName;
    private FoodCategory category;
    private EditText mEditTextName;
    private Spinner mSpinner;
    private Button mButton;
    private Button mCancelButton;
    private ConstraintLayout mConstraintLayout;
    private List<FoodCategory> dbList;
    private boolean onSelectFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_item);

        mConstraintLayout = (ConstraintLayout) findViewById(R.id.addShopConstraint);
        mEditTextName = (EditText) findViewById(R.id.textNameShop);
        mSpinner = (Spinner) findViewById(R.id.categorySpinnerShop);
        mButton = (Button) findViewById(R.id.addItemBtnShop);
        mCancelButton = (Button) findViewById(R.id.cancelItemShop);

        // Hide keyboard on touch
        mConstraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DismissKeyboard(v);
                return true;
            }
        });

        // Add Item Button onClickListener
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSpinner.isSelected() || mEditTextName.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Some information seems to be empty, please try again.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    addItem();
                }
            }
        });

        // Cancel Item Button onClickListener
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelItem();
            }
        });

        // Category Field + Load Content
        Thread getAllThread = new Thread(new Runnable() {
            @Override
            public void run() {
                dbList = MainActivity.appDatabase.foodCategoryDao().getAll();
            }
        });
        getAllThread.start();

        try {
            getAllThread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupt Occurred");
            e.printStackTrace();
        }

        ArrayList<FoodCategory> arrList = new ArrayList<>();

        if(!dbList.isEmpty()) {
            for (int i = 0; i < dbList.size(); i++) {
                arrList.add(dbList.get(i));
            }
        }

        ArrayAdapter<FoodCategory> adapter = new ArrayAdapter<FoodCategory>(getApplicationContext(), android.R.layout.simple_spinner_item, arrList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(onSelectFlag) {

                } else {
                    onSelectFlag = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addItem() {
        itemName = mEditTextName.getText().toString();
        category = (FoodCategory) mSpinner.getSelectedItem();

        final ShoppingItem newItem = new ShoppingItem();
        newItem.setItemName(itemName);
        newItem.setCategory(category);

        Thread addItemThread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.appDatabase.shoppingItemDao().insertOne(newItem);
            }
        });

        addItemThread.start();

        try {
            addItemThread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupt Occurred");
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Item added successfully", Toast.LENGTH_SHORT).show();

        Intent intentInv = new Intent(getApplicationContext(), ShoppingList.class);
        startActivity(intentInv);
    }

    public void cancelItem() {
        finish();
    }


    private void DismissKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
