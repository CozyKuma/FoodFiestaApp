package com.example.cozykuma.foodinventory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class CategoryDetails extends AppCompatActivity {

    private String categoryName;
    private int datePreset;
    private TextView mName;
    private TextView mDatePreset;
    private EditText mEditName;
    private EditText mEditDatePreset;
    private Button mConfirm;
    private Button mCancel;
    private int position;
    private Button mRemoveButton;
    // = (TextView) findViewById(R.id.textViewCategory);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        position = getIntent().getExtras().getInt("Position");

        mName = (TextView) findViewById(R.id.textViewCategory);
        mDatePreset = (TextView) findViewById(R.id.datePresetTitle);
        mEditName = (EditText) findViewById(R.id.textNameCategory);
        mEditDatePreset = (EditText) findViewById(R.id.datePresetNum);
        mConfirm = (Button) findViewById(R.id.ConfirmCategory);
        mCancel = (Button) findViewById(R.id.cancelCategory);

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

       /* mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adbRemove = new AlertDialog.Builder(CategoryDetails.this);
                adbRemove.setIcon(R.drawable.ic_remove_black_24dp); //  <---- PLACEHOLDER(?)
                adbRemove.setTitle("Remove "+FoodCategory.getCategoryList().get(position).getCategoryName()+"?");
                adbRemove.setMessage("This Category will be deleted");
                adbRemove.setNegativeButton("Cancel",null);
                adbRemove.setPositiveButton("Remove", new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        remove();
                    }
                });
                adbRemove.show();
            }
        });*/

    }


    public void confirm(){
        FoodCategory.getCategoryList().get(position).setCategoryName((mEditName.getText().toString()));
        FoodCategory.getCategoryList().get(position).setDatePreset(Integer.parseInt(mEditDatePreset.getText().toString()));
        Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
        startActivity(intent);
    }

    /*public void remove(){


    }*/
}
