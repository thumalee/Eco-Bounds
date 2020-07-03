package com.example.shoppingcart.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingcart.Interface.ItemClickListner;
import com.example.shoppingcart.R;

public class CompanyUserViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView compName,comPhone,comType,comCity,comEmail;
    public ItemClickListner listner;

    public CompanyUserViewHolder(@NonNull View itemView) {
        super(itemView);

        compName=(TextView) itemView.findViewById(R.id.comp_name);
        comPhone=(TextView) itemView.findViewById(R.id.comp_phoneNum);
        comType= itemView.findViewById(R.id.comp_type);
        comCity= itemView.findViewById(R.id.comp_City);
        comEmail = itemView.findViewById(R.id.comp_Email);
        
    }


    public void setItemClickListner(ItemClickListner listner){

        this.listner=listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

}
