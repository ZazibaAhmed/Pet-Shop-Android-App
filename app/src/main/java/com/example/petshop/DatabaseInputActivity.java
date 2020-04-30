package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petshop.Model.Food;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseInputActivity extends AppCompatActivity {

    private Button AddtoDataBaseBtn,AdminSignoutBtn;
    private EditText foodname_input,foodprice_input,foodquantity_input,
            fooddescription_input,foodimg_input;
    Spinner foodtype_input;

    DatabaseReference databaseFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_input);

        databaseFood= FirebaseDatabase.getInstance().getReference("Food");

        AddtoDataBaseBtn=(Button)findViewById(R.id.admininput_btn);
        AdminSignoutBtn=(Button)findViewById(R.id.adminSignout_btn);

        foodname_input=(EditText) findViewById(R.id.foodname_input);
        foodprice_input=(EditText)findViewById(R.id.foodprice_input);
        foodquantity_input=(EditText)findViewById(R.id.foodquantity_input);
        foodquantity_input=(EditText)findViewById(R.id.foodquantity_input);
        fooddescription_input=(EditText)findViewById(R.id.fooddescription_input);
        foodimg_input=(EditText)findViewById(R.id.foodimg_input);
        foodtype_input=(Spinner)findViewById(R.id.foodtype_input);

        AddtoDataBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AddFood();
            }
        });

        AdminSignoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DatabaseInputActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void AddFood(){
        String name = foodname_input.getText().toString();
        String price = foodprice_input.getText().toString();
        String quantity = foodquantity_input.getText().toString();
        String type = foodtype_input.getSelectedItem().toString();
        String description = fooddescription_input.getText().toString();
        String img = foodimg_input.getText().toString();

        if((!TextUtils.isEmpty(name))||(!TextUtils.isEmpty(price))||(!TextUtils.isEmpty(quantity))
                ||(!TextUtils.isEmpty(type))){

            int quantityInt=Integer.parseInt(quantity);
            String id=databaseFood.push().getKey();
            Food foodProduct=new Food(id,name,price,quantityInt,type,img,description);
            databaseFood.child(id).setValue(foodProduct);

            Toast.makeText(DatabaseInputActivity.this, "Food product added.",
                    Toast.LENGTH_SHORT).show();
            foodname_input.setText("");
            foodprice_input.setText("");
            fooddescription_input.setText("");
            foodquantity_input.setText("");


        }
        else{
            Toast.makeText(DatabaseInputActivity.this, "Empty field/fields.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
