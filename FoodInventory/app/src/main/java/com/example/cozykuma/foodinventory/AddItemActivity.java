package com.example.cozykuma.foodinventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void goToAddItemActivity(View view) {
       Intent intent = new Intent (this, AddItemActivity.class);
        startActivity(intent);
    }
}
