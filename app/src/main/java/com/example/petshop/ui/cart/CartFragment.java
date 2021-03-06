package com.example.petshop.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.petshop.CartActivity;
import com.example.petshop.R;
import com.example.petshop.SuppliesActivity;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.cart_items_layout, container, false);
        startActivity(new Intent(getActivity(), CartActivity.class));
        return root;
    }
}