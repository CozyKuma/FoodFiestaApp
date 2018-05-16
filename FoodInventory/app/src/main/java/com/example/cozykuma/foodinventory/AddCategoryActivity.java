package com.example.cozykuma.foodinventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private Button mAddCategory;
    private Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

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


    }

    public void addCategory(){
        categoryName = mEditName.getText().toString();
        datePreset = Integer.parseInt(mEditDatePreset.getText().toString());
        FoodCategory newCategory = new FoodCategory(categoryName,datePreset);
        Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
        startActivity(intent);
    }
}
