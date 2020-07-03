package com.example.shoppingcart.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingcart.Interface.ItemClickListner;
import com.example.shoppingcart.R;

import org.w3c.dom.Text;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textProductName,textProductPrice,textProductQuantity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        textProductName=itemView.findViewById(R.id.prod_name_cart);
        textProductPrice=itemView.findViewById(R.id.prod_price_cart);
        textProductQuantity=itemView.findViewById(R.id.prod_quan_cart);
    }


    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v,getAdapterPosition(),false);
    }


    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
