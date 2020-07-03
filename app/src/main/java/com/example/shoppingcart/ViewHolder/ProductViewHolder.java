package com.example.shoppingcart.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingcart.Interface.ItemClickListner;
import com.example.shoppingcart.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDes, txtProductPrice;
    public ImageView imgView;
    public ItemClickListner listner;


    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imgView = (ImageView) itemView.findViewById(R.id.product_img);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDes = (TextView) itemView.findViewById(R.id.product_des);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListner(ItemClickListner listner)
    {

        this.listner = listner;

    }

    @Override
    public void onClick(View view)
    {

        listner.onClick(view, getAdapterPosition(), false);

    }
}
