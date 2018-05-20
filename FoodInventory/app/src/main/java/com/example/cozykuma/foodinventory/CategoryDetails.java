package com.example.cozykuma.foodinventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;


public class CategoryDetails extends AppCompatActivity {

    private String categoryName;
    private int datePreset;
    private TextView mName;
    private TextView mDatePreset;
    private EditText mEditName;
    private EditText mEditDatePreset;
    private ConstraintLayout mConstraintLayout;
    private Button mConfirm;
    private Button mCancel;
    private int position;
    private Button mRemoveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        position = getIntent().getExtras().getInt("Position");

        setTitle("Category - " + FoodCategory.getCategoryList().get(position).getCategoryName().toString());

        mConstraintLayout = (ConstraintLayout) findViewById(R.id.catDetailConstraint);
        mName = (TextView) findViewById(R.id.textViewCategory);
        mDatePreset = (TextView) findViewById(R.id.datePresetTitle);
        mEditName = (EditText) findViewById(R.id.textNameCategory);
        mEditDatePreset = (EditText) findViewById(R.id.datePresetNum);
        mConfirm = (Button) findViewById(R.id.ConfirmCategory);
        mCancel = (Button) findViewById(R.id.cancelCategory);
        mRemoveButton = (Button) findViewById(R.id.removeCategory);

        mEditName.setText(FoodCategory.getCategoryList().get(position).getCategoryName().toString());
        mEditDatePreset.setText(String.valueOf(FoodCategory.getCategoryList().get(position).getDatePreset()));

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
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

       mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adbRemove = new AlertDialog.Builder(CategoryDetails.this);
                adbRemove.setIcon(R.drawable.ic_remove_black_24dp); //  <---- PLACEHOLDER(?)
                adbRemove.setTitle("Remove "+FoodCategory.getCategoryList().get(position).getCategoryName()+"?");
                adbRemove.setMessage("This Category will be deleted with the items within.");
                adbRemove.setNegativeButton("Cancel",null);
                adbRemove.setPositiveButton("Remove", new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Thread removeCat = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                remove(FoodCategory.getCategoryList().get(position));
                            }
                        });
                        removeCat.start();

                        try {
                            removeCat.join();
                        } catch (InterruptedException e) {
                            System.out.println("Interrupt Occurred");
                            e.printStackTrace();
                        }
                        finish();
                    }
                });
                adbRemove.show();
            }
        });

    }


    public void confirm(){
        FoodCategory.getCategoryList().get(position).setCategoryName((mEditName.getText().toString()));
        FoodCategory.getCategoryList().get(position).setDatePreset(Integer.parseInt(mEditDatePreset.getText().toString()));

        Thread updateItem = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.appDatabase.foodCategoryDao().update(FoodCategory.getCategoryList().get(position));
            }
        });
        updateItem.start();

        try {
            updateItem.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interruption Occurred");
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
        startActivity(intent);
    }

    private void DismissKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void remove(FoodCategory category){
        ArrayList<FoodItem> itemsInCat = new ArrayList<>();
        List<FoodItem> itemListCat = MainActivity.appDatabase.foodItemDao().findByCategoryName(category.getCategoryName());

        for(int i=0; i<itemListCat.size(); i++) {
            itemsInCat.add(itemListCat.get(i));
        }

        MainActivity.appDatabase.foodItemDao().deleteMultiple(itemsInCat);
        MainActivity.appDatabase.foodCategoryDao().delete(category);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
