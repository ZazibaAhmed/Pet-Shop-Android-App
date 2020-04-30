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
import com.example.petshop.Model.Food;
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

public class Sup_Prod_DetailsActivity extends AppCompatActivity {

    private ElegantNumberButton selegantbutton;
    TextView sproduct_name_details,sproduct_description_details,sproduct_price_details;
    ImageView sproduct_image_details;
    Button saddToCartBtn;
    String sproductID="";
    int squantity,selegantBtnNum;
    DatabaseReference cartListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup__prod__details);

        cartListRef=FirebaseDatabase.getInstance().getReference("Cart");

        sproductID=getIntent().getStringExtra("spid");
        saddToCartBtn=findViewById(R.id.saddToCart_btn);
        sproduct_name_details=findViewById(R.id.sproduct_name_details);
        sproduct_price_details=findViewById(R.id.sproduct_price_details);
        sproduct_description_details=findViewById(R.id.sproduct_description_details);
        sproduct_image_details=findViewById(R.id.sproduct_image_details);

        getSupProductDetails(sproductID);


        selegantbutton = (ElegantNumberButton) findViewById(R.id.selegantbutton);
        selegantBtnNum = Integer.parseInt(selegantbutton.getNumber());
        selegantbutton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                selegantBtnNum = Integer.parseInt(selegantbutton.getNumber());

            }
        });

        saddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selegantBtnNum>squantity){
                    Toast.makeText(Sup_Prod_DetailsActivity.this, "Only "+squantity+" items in stock.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selegantBtnNum==0){
                    Toast.makeText(Sup_Prod_DetailsActivity.this, "Select quantity!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    addToCartList();
                }
            }
        });




    }

    public void getSupProductDetails(String productID)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Supplies");
        productRef.keepSynced(true);
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Supplies sup=dataSnapshot.getValue(Supplies.class);
                    sproduct_name_details.setText(sup.getsupName());
                    sproduct_description_details.setText(sup.getsupDescription());
                    sproduct_price_details.setText(sup.getsupPrice());
                    squantity=sup.getsupQuantity();
                    Picasso.get().load(sup.getsupImg()).into(sproduct_image_details);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void addToCartList(){
        Toast.makeText(Sup_Prod_DetailsActivity.this, "Added to cart",
                Toast.LENGTH_SHORT).show();
        final String pid=cartListRef.push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        final String domain = email .substring(0,email .indexOf("."));

        String pname=sproduct_name_details.getText().toString();
        String price=sproduct_price_details.getText().toString();
        int quantity=selegantBtnNum;

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
                                                Intent intent=new Intent(Sup_Prod_DetailsActivity.
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
