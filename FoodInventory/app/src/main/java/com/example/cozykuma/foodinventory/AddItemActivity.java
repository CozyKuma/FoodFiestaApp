package com.example.cozykuma.foodinventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    private String itemName;
    private FoodCategory category;
    private String expireDate;
    private EditText mEditTextName;
    private EditText mEditTextDate;
    private Spinner mSpinner;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mEditTextName = (EditText) findViewById(R.id.textName);
        mEditTextDate = (EditText) findViewById(R.id.textDate);
        mSpinner = (Spinner) findViewById(R.id.categorySpinner);
        mButton = (Button) findViewById(R.id.addItemBtn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItem();
            }
        });

        ArrayAdapter<FoodCategory> adapter;
    }

    public void AddItem() {
        itemName = mEditTextName.getText().toString();
        //category = (FoodCategory) mSpinner.getSelectedItem();
        expireDate = mEditTextDate.getText().toString();

        FoodItem newItem = new FoodItem(itemName, expireDate);

        Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentInv);
    }
}
