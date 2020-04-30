package com.example.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.petshop.Model.Cart;
import com.example.petshop.Model.Medication;
import com.example.petshop.Model.Supplies;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Med_Prod_DetailsActivity extends AppCompatActivity {

    private ElegantNumberButton melegantbutton;
    TextView mproduct_name_details,mproduct_description_details,mproduct_price_details;
    ImageView mproduct_image_details;
    Button maddToCartBtn;
    String mproductID="";
    int mquantity,melegantBtnNum;
    DatabaseReference cartListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med__prod__details);

        cartListRef=FirebaseDatabase.getInstance().getReference("Cart");

        mproductID=getIntent().getStringExtra("mpid");
        maddToCartBtn=findViewById(R.id.maddToCart_btn);
        mproduct_name_details=findViewById(R.id.mproduct_name_details);
        mproduct_price_details=findViewById(R.id.mproduct_price_details);
        mproduct_description_details=findViewById(R.id.mproduct_description_details);
        mproduct_image_details=findViewById(R.id.mproduct_image_details);

        getMedProductDetails(mproductID);

        melegantbutton = (ElegantNumberButton) findViewById(R.id.melegantbutton);
        melegantBtnNum = Integer.parseInt(melegantbutton.getNumber());
        melegantbutton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                melegantBtnNum = Integer.parseInt(melegantbutton.getNumber());

            }
        });

        maddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(melegantBtnNum>mquantity){
                    Toast.makeText(Med_Prod_DetailsActivity.this, "Only "+mquantity+" items in stock.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(melegantBtnNum==0){
                    Toast.makeText(Med_Prod_DetailsActivity.this, "Select quantity!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    addToCartList();
                }
            }
        });
    }

    public void getMedProductDetails(String productID)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Medication");
        productRef.keepSynced(true);
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Medication med=dataSnapshot.getValue(Medication.class);
                    mproduct_name_details.setText(med.getmedName());
                    mproduct_description_details.setText(med.getmedDescription());
                    mproduct_price_details.setText(med.getmedPrice());
                    mquantity=med.getmedQuantity();
                    Picasso.get().load(med.getmedImg()).into(mproduct_image_details);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void addToCartList(){
        Toast.makeText(Med_Prod_DetailsActivity.this, "Added to cart",
                Toast.LENGTH_SHORT).show();

        final String pid=cartListRef.push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        final String domain = email .substring(0,email .indexOf("."));

        String pname=mproduct_name_details.getText().toString();
        String price=mproduct_price_details.getText().toString();
        int quantity=melegantBtnNum;

        final Cart cartProduct=new Cart(pid,pname,price,quantity);
        //cartListRef.child(pid).child(domain).child("Products").setValue(cartProduct);

        cartListRef.child("User View").child(domain).child("Products")
                .child(pid).setValue(cartProduct)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListRef.child("Admin View").child(domain).child("Products")
                                    .child(pid).setValue(cartProduct)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_SHORT)
                                                        .show();
                                                Intent intent=new Intent(Med_Prod_DetailsActivity.
                                                        this,HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
