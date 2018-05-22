package com.example.cozykuma.foodinventory;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddCategoryActivity extends AppCompatActivity {

    private String categoryName;
    private int datePreset;
    private TextView mName;
    private TextView mDatePreset;
    private EditText mEditName;
    private EditText mEditDatePreset;
    private ConstraintLayout mConstraintLayout;
    private Button mAddCategory;
    private Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        setTitle("Create Category");

        mConstraintLayout = (ConstraintLayout) findViewById(R.id.addCatConstraint);
        mName = (TextView) findViewById(R.id.textViewCategory);
        mDatePreset = (TextView) findViewById(R.id.datePresetTitle);
        mEditName = (EditText) findViewById(R.id.textNameCategory);
        mEditDatePreset = (EditText) findViewById(R.id.datePresetNum);
        mAddCategory = (Button) findViewById(R.id.addCategory);
        mCancel = (Button) findViewById(R.id.cancelCategory);

        mAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Hide keyboard on touch
        mConstraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DismissKeyboard(v);
                return true;
            }
        });

    }

    public void addCategory(){
        categoryName = mEditName.getText().toString();
        datePreset = Integer.parseInt(mEditDatePreset.getText().toString());

        final FoodCategory newCategory = new FoodCategory(categoryName,datePreset);

        Thread addCategoryItem = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.appDatabase.foodCategoryDao().insertOne(newCategory);
            }
        });

        addCategoryItem.start();

        try {
            addCategoryItem.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupt Occurred");
            e.printStackTrace();
        }

        CategoryActivity.notifyCatAdapter();

        Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
        startActivity(intent);
    }

    private void DismissKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
