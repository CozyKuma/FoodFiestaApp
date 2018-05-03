package com.example.cozykuma.foodinventory;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ItemDetails extends AppCompatActivity {

    private int position;
    private TextView mItemNameText;
    private Spinner mCategoryName;
    private TextView mItemDateText;
    private Button mButton;
    private CheckBox mCheckBoxNotify;
    private CheckBox mCheckBoxOpen;
    private Button mCancelButton;
    private Button mRemoveButton;
    private boolean notifyMe;
    private boolean itemOpen;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText mAmountText;
    private int percentage;
    ImageButton mAmountAddBtn;
    ImageButton mAmountRemovebBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);


        position = getIntent().getExtras().getInt("Position");

        mItemNameText = (TextView) findViewById(R.id.textName);
        mCategoryName = (Spinner)findViewById(R.id.categorySpinner);
        mItemDateText = (TextView)findViewById(R.id.dateTextView);
        mButton = (Button)findViewById(R.id.editItemBtn);
        mCancelButton = (Button)findViewById(R.id.cancelItem);
        mRemoveButton = (Button)findViewById(R.id.removeItem);
        mCheckBoxNotify = (CheckBox)findViewById(R.id.checkBoxNotify);
        mCheckBoxOpen = (CheckBox)findViewById(R.id.checkBoxOpen);
        mAmountText = (EditText)findViewById(R.id.amountEditText);
        mAmountAddBtn = (ImageButton)findViewById(R.id.addAmount);
        mAmountRemovebBtn = (ImageButton)findViewById(R.id.removeAmount);
        percentage = 10;

        ArrayAdapter<FoodCategory> adapter = new ArrayAdapter<FoodCategory>(getApplicationContext(), android.R.layout.simple_spinner_item, FoodCategory.getCategoryList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCategoryName.setAdapter(adapter);
        mItemNameText.setText(FoodItem.getListOfItems().get(position).getItemName());
        mCategoryName.setSelection(getIndex(mCategoryName, FoodItem.getListOfItems().get(position).getCategory().toString()));
        Calendar calToString = Calendar.getInstance();
        calToString.setTime(FoodItem.getListOfItems().get(position).getDateExpire());
        calToString.add(Calendar.MONTH, 1);
        String dateString = calToString.get(Calendar.DAY_OF_MONTH) + "-" + calToString.get(Calendar.MONTH) + "-" + calToString.get(Calendar.YEAR);
        mItemDateText.setText(dateString);

        mCheckBoxOpen.setChecked(FoodItem.getListOfItems().get(position).isOpened());
        mCheckBoxNotify.setChecked(FoodItem.getListOfItems().get(position).isNotifyMe());

        mAmountText.setText(String.valueOf(FoodItem.getListOfItems().get(position).getAmountLeft()));


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

        mAmountAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount();
            }
        });

        mAmountRemovebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAmount();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adbRemove = new AlertDialog.Builder(ItemDetails.this);
                adbRemove.setIcon(R.drawable.ic_remove_black_24dp); //  <---- PLACEHOLDER(?)
                adbRemove.setTitle("Remove "+FoodItem.getListOfItems().get(position).getItemName()+"?");
                adbRemove.setMessage("This item will be removed from your Inventory permanently");
                adbRemove.setNegativeButton("Cancel",null);
                adbRemove.setPositiveButton("Remove", new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        remove();
                    }
                });
                adbRemove.show();
            }
        });
    }


    public void confirm() {
        FoodItem.getListOfItems().get(position).setItemName((String) mItemNameText.getText().toString());
        FoodItem.getListOfItems().get(position).setCategory((FoodCategory) mCategoryName.getSelectedItem());
        FoodItem.getListOfItems().get(position).setDateExpire((String) mItemDateText.getText().toString());
        FoodItem.getListOfItems().get(position).setOpened(mCheckBoxOpen.isChecked());
        FoodItem.getListOfItems().get(position).setNotifyMe(mCheckBoxNotify.isChecked());
        FoodItem.getListOfItems().get(position).setDaysLeft(FoodItem.daysBetween(new Date(), FoodItem.getListOfItems().get(position).getDateExpire()));
        if (Integer.parseInt(mAmountText.getText().toString()) > 100){
            mAmountText.setText("100");
        }
        FoodItem.getListOfItems().get(position).setAmountLeft(Integer.parseInt(String.valueOf(mAmountText.getText())));
        Intent intentInv = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentInv);
    }

    public void cancel(){
        finish();
    }

    public void remove(){
        FoodItem.getListOfItems().remove(position);
        finish();
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    public void addAmount() {
        if (FoodItem.getListOfItems().get(position).getAmountLeft() < 100) {
            FoodItem.getListOfItems().get(position).useAmount(-percentage);
            mAmountText.setText(String.valueOf(FoodItem.getListOfItems().get(position).getAmountLeft()), TextView.BufferType.EDITABLE);
        }
        if (FoodItem.getListOfItems().get(position).getAmountLeft() >= 100){
            FoodItem.getListOfItems().get(position).setAmountLeft(100);
            mAmountText.setText("100");
        }
    }

    public void removeAmount() {
        if (FoodItem.getListOfItems().get(position).getAmountLeft() > 0) {
            FoodItem.getListOfItems().get(position).useAmount(percentage);
            mAmountText.setText(String.valueOf(FoodItem.getListOfItems().get(position).getAmountLeft()), TextView.BufferType.EDITABLE);
        }
        if (FoodItem.getListOfItems().get(position).getAmountLeft() <= 0){
            FoodItem.getListOfItems().get(position).setAmountLeft(0);
            mAmountText.setText("0");
        }
    }
}
