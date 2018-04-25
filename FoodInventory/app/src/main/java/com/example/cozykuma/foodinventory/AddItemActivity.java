package com.example.cozykuma.foodinventory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {

    private String itemName;
    private FoodCategory category;
    private String expireDate;
    private EditText mEditTextName;
    private TextView mDateView;
    private Spinner mSpinner;
    private Button mButton;
    private Button mCancelButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mEditTextName = (EditText) findViewById(R.id.textName);
        mDateView = (TextView) findViewById(R.id.dateTextView);
        mSpinner = (Spinner) findViewById(R.id.categorySpinner);
        mButton = (Button) findViewById(R.id.addItemBtn);
        mCancelButton = (Button) findViewById(R.id.cancelItem);

        // Add Item Button onClickListener
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
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

        // Calendar onClickListener
        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddItemActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                }
            }
        );

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth + "-" + month + "-" + year;
                mDateView.setText(date);
            }
        };
    }

    public void addItem() {
        itemName = mEditTextName.getText().toString();
        category = (FoodCategory) mSpinner.getSelectedItem();
        expireDate = mDateView.getText().toString();

        FoodItem newItem = new FoodItem(itemName, expireDate, category);

        Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentInv);
    }

    public void cancelItem() {
        finish();
    }
}
