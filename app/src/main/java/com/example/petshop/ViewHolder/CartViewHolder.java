package com.example.petshop.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.Interface.ItemClickListener;
import com.example.petshop.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView txtproductName,txtproductPrice,txtproductQuantity;
    public ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtproductName=(TextView)itemView.findViewById(R.id.cart_product_name);
        txtproductPrice=(TextView)itemView.findViewById(R.id.cart_product_price);
        txtproductQuantity=itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
