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
import com.example.petshop.Model.Medication;
import com.example.petshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MedicationActivity extends AppCompatActivity {

    private DatabaseReference MedRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Medication> arraylist;
    FirebaseRecyclerOptions<Medication> options;
    FirebaseRecyclerAdapter<Medication, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        MedRef = FirebaseDatabase.getInstance().getReference().child("Medication");
        MedRef.keepSynced(true);

        recyclerView = findViewById(R.id.med_recyclerview);
        recyclerView.setHasFixedSize(true);
        // layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arraylist=new ArrayList<Medication>();
        options = new FirebaseRecyclerOptions.Builder<Medication>().setQuery(MedRef, Medication.class).build();

        adapter=new FirebaseRecyclerAdapter<Medication, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder,
                                            int i, @NonNull final Medication medication) {
                productViewHolder.txtproductName.setText(medication.getmedName());
                productViewHolder.txtproductPrice.setText(medication.getmedPrice());
                Picasso.get().load(medication.getmedImg()).into(productViewHolder.imageview);
                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MedicationActivity.this, Med_Prod_DetailsActivity.class);
                        intent.putExtra("mpid",medication.getmedid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ProductViewHolder(LayoutInflater.from(MedicationActivity.this).
                        inflate(R.layout.product_items_layout
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
