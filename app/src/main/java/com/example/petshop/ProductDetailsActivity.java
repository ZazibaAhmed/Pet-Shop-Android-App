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

import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private ElegantNumberButton elegantbutton;
    TextView fproduct_name_details,fproduct_description_details,fproduct_price_details;
    ImageView fproduct_image_details;
    Button faddToCartBtn;
    String fproductID="";
    int fquantity,elegantBtnNum;
    DatabaseReference cartListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        cartListRef=FirebaseDatabase.getInstance().getReference("Cart");

        fproductID=getIntent().getStringExtra("fpid");
        faddToCartBtn=findViewById(R.id.faddToCart_btn);
        fproduct_name_details=findViewById(R.id.fproduct_name_details);
        fproduct_price_details=findViewById(R.id.fproduct_price_details);
        fproduct_description_details=findViewById(R.id.fproduct_description_details);
        fproduct_image_details=findViewById(R.id.fproduct_image_details);

        getFoodProductDetails(fproductID);


        elegantbutton = (ElegantNumberButton) findViewById(R.id.elegantbutton);
        elegantBtnNum = Integer.parseInt(elegantbutton.getNumber());
        elegantbutton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegantBtnNum = Integer.parseInt(elegantbutton.getNumber());

            }
        });

        faddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(elegantBtnNum>fquantity){
                    Toast.makeText(ProductDetailsActivity.this, "Only "+fquantity+" items in stock.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(elegantBtnNum==0){
                    Toast.makeText(ProductDetailsActivity.this, "Select quantity!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    addToCartFoodList();
                }
            }
        });




    }

    public void getFoodProductDetails(String productID)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Food");
        productRef.keepSynced(true);

        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Food food=dataSnapshot.getValue(Food.class);
                    fproduct_name_details.setText(food.getfoodName());
                    fproduct_description_details.setText(food.getfoodDescription());
                    fproduct_price_details.setText(food.getfoodPrice());
                    fquantity=food.getfoodQuantity();
                    Picasso.get().load(food.getfoodImg()).into(fproduct_image_details);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void addToCartFoodList(){


        String pid=cartListRef.push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        final String domain = email .substring(0,email .indexOf("."));
        Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_SHORT)
                .show();
        String pname=fproduct_name_details.getText().toString();
        String price=fproduct_price_details.getText().toString();
        int quantity=elegantBtnNum;

        final Cart cartProduct=new Cart(pid,pname,price,quantity);
        //cartListRef.child(pid).child(domain).child("Products").setValue(cartProduct);


        /*final HashMap<String, Object> cartMap=new HashMap<>();
        cartMap.put("pid",fproductID);
        cartMap.put("pname",fproduct_name_details.getText().toString());
        cartMap.put("price",fproduct_price_details.getText().toString());
        cartMap.put("quantity",elegantBtnNum);
        cartMap.put("discount","");

        FirebaseAuth auth=FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();*/

        cartListRef.child("User View").child(domain).child("Products")
                .child(pid).setValue(cartProduct)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListRef.child("Admin View").child(domain).child("Products")
                                    .child(fproductID).setValue(cartProduct)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_SHORT)
                                                        .show();
                                                Intent intent=new Intent(ProductDetailsActivity.
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
