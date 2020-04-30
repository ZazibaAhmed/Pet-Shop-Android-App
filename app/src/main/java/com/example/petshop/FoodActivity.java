package com.example.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.petshop.Model.Food;
import com.example.petshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private DatabaseReference FoodRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Food> arraylist;
    FirebaseRecyclerOptions<Food> options;
    FirebaseRecyclerAdapter<Food, ProductViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        FoodRef = FirebaseDatabase.getInstance().getReference().child("Food");
        FoodRef.keepSynced(true);

        recyclerView = findViewById(R.id.food_recyclerview);
        recyclerView.setHasFixedSize(true);
       // layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arraylist=new ArrayList<Food>();


        options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(FoodRef, Food.class).build();
        adapter=new FirebaseRecyclerAdapter<Food, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int i, final @NonNull Food food) {
                productViewHolder.txtproductName.setText(food.getfoodName());
                productViewHolder.txtproductPrice.setText(food.getfoodPrice());
                Picasso.get().load(food.getfoodImg()).into(productViewHolder.imageview);
                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FoodActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("fpid",food.getfoodid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ProductViewHolder(LayoutInflater.from(FoodActivity.this).inflate(R.layout.product_items_layout
                        ,parent,false));
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
