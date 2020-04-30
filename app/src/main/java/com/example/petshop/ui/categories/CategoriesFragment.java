package com.example.petshop.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.petshop.FoodActivity;
import com.example.petshop.HomeActivity;
import com.example.petshop.LoginActivity;
import com.example.petshop.MedicationActivity;
import com.example.petshop.Model.Medication;
import com.example.petshop.R;
import com.example.petshop.RegisterActivity;
import com.example.petshop.SuppliesActivity;

public class CategoriesFragment extends Fragment {

    private CategoriesViewModel categoriesViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoriesViewModel =
                ViewModelProviders.of(this).get(CategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        ImageView foodBtn = (ImageView) root.findViewById(R.id.food_img);
        ImageView supplyBtn = (ImageView) root.findViewById(R.id.supply_img);
        ImageView medBtn = (ImageView) root.findViewById(R.id.med_img);
        foodBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), FoodActivity.class));

            }
        });

        supplyBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), SuppliesActivity.class));

            }
        });
        medBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(getActivity(), MedicationActivity.class));

            }
        });
        return root;
    }
}