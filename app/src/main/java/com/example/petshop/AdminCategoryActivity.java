package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminCategoryActivity extends AppCompatActivity {

    private Button FoodBtn,SuppliesBtn,MedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        FoodBtn=(Button)findViewById(R.id.foodBtn);
        SuppliesBtn=(Button)findViewById(R.id.suppliesBtn);
        MedBtn=(Button)findViewById(R.id.medBtn);

        FoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, DatabaseInputActivity.class);
                startActivity(intent);
            }
        });

        SuppliesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this, SupDbInputActivity.class);
                startActivity(intent);

            }
        });

        MedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this, MedDbInputActivity.class);
                startActivity(intent);

            }
        });
    }
}
