package com.example.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.petshop.Model.Food;
import com.example.petshop.Model.Supplies;
import com.example.petshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SuppliesActivity extends AppCompatActivity {

    private DatabaseReference SupRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Supplies> arraylist;
    FirebaseRecyclerOptions<Supplies> options;
    FirebaseRecyclerAdapter<Supplies, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies);

        SupRef = FirebaseDatabase.getInstance().getReference().child("Supplies");
        SupRef.keepSynced(true);

        recyclerView = findViewById(R.id.sup_recyclerview);
        recyclerView.setHasFixedSize(true);
        // layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arraylist=new ArrayList<Supplies>();
        options = new FirebaseRecyclerOptions.Builder<Supplies>().setQuery(SupRef, Supplies.class).build();

        adapter=new FirebaseRecyclerAdapter<Supplies, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i,
                                            final @NonNull Supplies supplies) {
                productViewHolder.txtproductName.setText(supplies.getsupName());
                productViewHolder.txtproductPrice.setText(supplies.getsupPrice());
                Picasso.get().load(supplies.getsupImg()).into(productViewHolder.imageview);
                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SuppliesActivity.this, Sup_Prod_DetailsActivity.class);
                        intent.putExtra("spid",supplies.getsupid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ProductViewHolder(LayoutInflater.from(SuppliesActivity.this).inflate(R.layout.product_items_layout
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
