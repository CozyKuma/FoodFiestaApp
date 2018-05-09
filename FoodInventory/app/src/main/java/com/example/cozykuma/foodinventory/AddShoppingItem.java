package com.example.cozykuma.foodinventory;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.content.Intent;

public class AddShoppingItem extends AppCompatActivity {

    private String itemName;
    private FoodCategory category;
    private EditText mEditTextName;
    private Spinner mSpinner;
    private Button mButton;
    private Button mCancelButton;
    private boolean onSelectFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_item);

        mEditTextName = (EditText) findViewById(R.id.textNameShop);
        mSpinner = (Spinner) findViewById(R.id.categorySpinnerShop);
        mButton = (Button) findViewById(R.id.addItemBtnShop);
        mCancelButton = (Button) findViewById(R.id.cancelItemShop);

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
        ArrayAdapter<FoodCategory> adapter = new ArrayAdapter<FoodCategory>(getApplicationContext(), android.R.layout.simple_spinner_item, FoodCategory.getCategoryList());
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

        public void addItem() {
        itemName = mEditTextName.getText().toString();
        category = (FoodCategory) mSpinner.getSelectedItem();

        final ShoppingItem newItem = new ShoppingItem(itemName, category);

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    AppDatabase.getAppDatabase(getApplicationContext()).shoppingItemDao().insertOne(newItem);
                    return null;
                }
            }.execute();

        Intent intentInv = new Intent(getApplicationContext(), ShoppingList.class);
        startActivity(intentInv);
    }

    public void cancelItem() {
        finish();
    }
}
