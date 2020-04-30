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
import com.example.petshop.Model.Supplies;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SupDbInputActivity extends AppCompatActivity {

    private Button SAddtoDataBaseBtn,SAdminSignoutBtn;
    private EditText supname_input,supprice_input,supquantity_input,
            supdescription_input,supimg_input;
    Spinner suptype_input;

    DatabaseReference databaseSupplies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_db_input);
        databaseSupplies= FirebaseDatabase.getInstance().getReference("Supplies");

        SAddtoDataBaseBtn=(Button)findViewById(R.id.adminsupinput_btn);
        SAdminSignoutBtn=(Button)findViewById(R.id.adminSupSignout_btn);

        supname_input=(EditText) findViewById(R.id.supname_input);
        supprice_input=(EditText)findViewById(R.id.supprice_input);
        supquantity_input=(EditText)findViewById(R.id.supquantity_input);
        supquantity_input=(EditText)findViewById(R.id.supquantity_input);
        supdescription_input=(EditText)findViewById(R.id.supdescription_input);
        supimg_input=(EditText)findViewById(R.id.supimg_input);
        suptype_input=(Spinner)findViewById(R.id.suptype_input);

        SAddtoDataBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSupplies();
            }
        });

        SAdminSignoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SupDbInputActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void AddSupplies(){
        String name = supname_input.getText().toString();
        String price = supprice_input.getText().toString();
        String quantity = supquantity_input.getText().toString();
        String type = suptype_input.getSelectedItem().toString();
        String description = supdescription_input.getText().toString();
        String img = supimg_input.getText().toString();

        if((!TextUtils.isEmpty(name))||(!TextUtils.isEmpty(price))||(!TextUtils.isEmpty(quantity))
                ||(!TextUtils.isEmpty(type))){

            int quantityInt=Integer.parseInt(quantity);
            String id=databaseSupplies.push().getKey();
            Supplies supProduct=new Supplies(id,name,price,quantityInt,type,img,description);
            databaseSupplies.child(id).setValue(supProduct);
            Toast.makeText(SupDbInputActivity.this, "Supply product added.",
                    Toast.LENGTH_SHORT).show();
            supname_input.setText("");
            supprice_input.setText("");
            supdescription_input.setText("");
            supquantity_input.setText("");


        }
        else{
            Toast.makeText(SupDbInputActivity.this, "Empty field/fields.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
