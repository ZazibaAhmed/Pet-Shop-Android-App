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

import com.example.petshop.Model.Medication;
import com.example.petshop.Model.Supplies;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedDbInputActivity extends AppCompatActivity {

    private Button mAddtoDataBaseBtn,mAdminSignoutBtn;
    private EditText medname_input,medprice_input,medquantity_input,
            meddescription_input,medimg_input;
    Spinner medtype_input;

    DatabaseReference databaseMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_db_input);

        databaseMed= FirebaseDatabase.getInstance().getReference("Medication");

        mAddtoDataBaseBtn=(Button)findViewById(R.id.madmininput_btn);
        mAdminSignoutBtn=(Button)findViewById(R.id.adminMedSignout_btn);

        medname_input=(EditText) findViewById(R.id.medname_input);
        medprice_input=(EditText)findViewById(R.id.medprice_input);
        medquantity_input=(EditText)findViewById(R.id.medquantity_input);
        medquantity_input=(EditText)findViewById(R.id.medquantity_input);
        meddescription_input=(EditText)findViewById(R.id.meddescription_input);
        medimg_input=(EditText)findViewById(R.id.medimg_input);
        medtype_input=(Spinner)findViewById(R.id.medtype_input);

        mAddtoDataBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMed();
            }
        });

        mAdminSignoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MedDbInputActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void AddMed(){
        String name = medname_input.getText().toString();
        String price = medprice_input.getText().toString();
        String quantity = medquantity_input.getText().toString();
        String type = medtype_input.getSelectedItem().toString();
        String description = meddescription_input.getText().toString();
        String img = medimg_input.getText().toString();

        if((!TextUtils.isEmpty(name))||(!TextUtils.isEmpty(price))||(!TextUtils.isEmpty(quantity))
                ||(!TextUtils.isEmpty(type))){

            int quantityInt=Integer.parseInt(quantity);
            String id=databaseMed.push().getKey();
            Medication medProduct=new Medication(id,name,price,quantityInt,type,img,description);
            databaseMed.child(id).setValue(medProduct);
            Toast.makeText(MedDbInputActivity.this, "Medication product added.",
                    Toast.LENGTH_SHORT).show();
            medname_input.setText("");
            medprice_input.setText("");
            meddescription_input.setText("");
            medquantity_input.setText("");


        }
        else{
            Toast.makeText(MedDbInputActivity.this, "Empty field/fields.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
