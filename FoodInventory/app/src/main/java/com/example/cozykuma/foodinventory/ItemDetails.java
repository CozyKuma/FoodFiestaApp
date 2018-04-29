package com.example.cozykuma.foodinventory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;


public class ItemDetails extends AppCompatActivity {

    private int position;
    private TextView mItemNameText;
    private Spinner mCategoryName;
    private TextView mItemDateText;
    private Button mButton;
    private Button mCancelButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        position = getIntent().getExtras().getInt("Position");

        mItemNameText = (TextView) findViewById(R.id.textName);
        mCategoryName = (Spinner)findViewById(R.id.categorySpinner);
        mItemDateText = (TextView)findViewById(R.id.dateTextView);
        mButton = (Button)findViewById(R.id.editItemBtn);

        ArrayAdapter<FoodCategory> adapter = new ArrayAdapter<FoodCategory>(getApplicationContext(), android.R.layout.simple_spinner_item, FoodCategory.getCategoryList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCategoryName.setAdapter(adapter);
        mItemNameText.setText(FoodItem.getListOfItems().get(position).getItemName());

        mItemDateText.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Calendar cal = Calendar.getInstance();
                 int year = cal.get(Calendar.YEAR);
                 int month = cal.get(Calendar.MONTH);
                 int day = cal.get(Calendar.DAY_OF_MONTH);

                 DatePickerDialog datePickerDialog = new DatePickerDialog(ItemDetails.this,
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
                mItemDateText.setText(date);
            }
        };



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });

       // categoryName.setSelection(FoodItem.getListOfItems().get(position));


        //itemDateText.setText((CharSequence) FoodItem.getListOfItems().get(position).getDateExpire());
        }

    public void confirm() {
        FoodItem.getListOfItems().get(position).setItemName((String) mItemNameText.getText().toString());
        FoodItem.getListOfItems().get(position).setCategory((FoodCategory) mCategoryName.getSelectedItem());
        Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentInv);
    }
}
