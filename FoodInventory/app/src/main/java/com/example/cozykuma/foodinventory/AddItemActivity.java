package com.example.cozykuma.foodinventory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    private String itemName;
    private FoodCategory category;
    private String expireDate;
    private boolean notifyMe;
    private boolean itemOpen;
    private EditText mEditTextName;
    private TextView mDateView;
    private Spinner mSpinner;
    private Button mButton;
    private Button mCancelButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CheckBox mCheckBoxNotify;
    private CheckBox mCheckBoxOpen;
    private EditText quantityView;
    private org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
    private boolean onSelectFlag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mEditTextName = (EditText) findViewById(R.id.textName);
        mDateView = (TextView) findViewById(R.id.dateTextView);
        mSpinner = (Spinner) findViewById(R.id.categorySpinner);
        mButton = (Button) findViewById(R.id.addItemBtn);
        mCancelButton = (Button) findViewById(R.id.cancelItem);
        mCheckBoxNotify = (CheckBox) findViewById(R.id.checkBoxNotify);
        mCheckBoxOpen = (CheckBox) findViewById(R.id.checkBoxOpen);
        quantityView = (EditText) findViewById(R.id.quantityNum);
        ImageButton addQuantityBtn = (ImageButton) findViewById(R.id.addQuantity);
        ImageButton removeQuantityBtn = (ImageButton) findViewById(R.id.removeQuantity);

        if(FoodItem.getNotifySetting()) {
            mCheckBoxNotify.setChecked(true);
        } else {
            mCheckBoxNotify.setChecked(false);
        }

        // Add Item Button onClickListener
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDateView.getText().toString().isEmpty() || mSpinner.isSelected() || mDateView.getText().toString().equals("Select Date") || mEditTextName.getText().toString().isEmpty()) {
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

        //Add & Remove button onClickListeners
        addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuantity();
            }
        });
        removeQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeQuantity();
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
                    String dateString = addDatePreset((FoodCategory) adapterView.getItemAtPosition(i));
                    mDateView.setText(dateString);
                } else {
                    onSelectFlag = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Calendar onClickListener
        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddItemActivity.this,
                        android.R.style.Theme_Holo_DialogWhenLarge,
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

                DateTime jodaDate = new DateTime();
                jodaDate = jodaDate.withDate(year, month, dayOfMonth);

                toastDaysToExpire(new Date(), jodaDate.toDate());
            }
        };
    }

    public void addItem() {
        itemName = mEditTextName.getText().toString();
        category = (FoodCategory) mSpinner.getSelectedItem();
        expireDate = mDateView.getText().toString();
        notifyMe = mCheckBoxNotify.isChecked();
        itemOpen = mCheckBoxOpen.isChecked();
        String quantityText = quantityView.getEditableText().toString();
        int quantity = Integer.parseInt(quantityText);
        final ArrayList<FoodItem> quantityList = new ArrayList<>();

        for (int i=0; i<quantity; i++) {
        FoodItem foodItem = new FoodItem();
        foodItem.setItemName(itemName);
        foodItem.setCategory(category);
        foodItem.setDateExpire(expireDate);
        foodItem.setDaysLeft(FoodItem.daysBetween(new Date(), foodItem.getDateExpire()));
        foodItem.setNotifyMe(notifyMe);
        foodItem.setOpened(itemOpen);

            quantityList.add(foodItem);
        }

        Thread insertItems = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(getApplicationContext()).foodItemDao().insertMultiple(quantityList);
            }
        });

        insertItems.start();

        try {
            insertItems.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interrupt Occurred");
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Item(s) added successfully", Toast.LENGTH_SHORT).show();

        Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentInv);
    }

    public void cancelItem() {
        finish();
    }

    public void addQuantity() {
        int current = Integer.parseInt(quantityView.getEditableText().toString());
        current += 1;
        quantityView.setText(String.valueOf(current), TextView.BufferType.EDITABLE);
    }

    public void removeQuantity() {
        int current = Integer.parseInt(quantityView.getEditableText().toString());
        if(current > 1) {
            current -= 1;
            quantityView.setText(String.valueOf(current), TextView.BufferType.EDITABLE);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "You can't reduce the quantity below 1", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public String addDatePreset(FoodCategory category) {
        Date today = new Date();
        DateTime jodaToday = new DateTime(today);
        jodaToday = jodaToday.plusDays(category.getDatePreset());
        String dateString = jodaToday.getDayOfMonth() + "-" + jodaToday.getMonthOfYear() + "-" + jodaToday.getYear();

        toastDaysToExpire(today, jodaToday.toDate());

        return dateString;
    }

    public void toastDaysToExpire(Date d1, Date d2) {
        int daysToExpire = FoodItem.daysBetween(d1, d2);
        if(daysToExpire > 0) {
            Toast.makeText(getApplicationContext(), "The item will expire in " + daysToExpire + " days.", Toast.LENGTH_SHORT).show();
        }
    }
}
