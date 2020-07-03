package com.example.shoppingcart.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.shoppingcart.Interface.ItemClickListner;
import com.example.shoppingcart.R;

public class CompanyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView compName,comPhone;
    public ItemClickListner listner;

    public CompanyViewHolder(@NonNull View itemView) {
        super(itemView);

        compName=(TextView) itemView.findViewById(R.id.company_names);
        comPhone=(TextView) itemView.findViewById(R.id.company_phones);
    }

    public void setItemClickListner(ItemClickListner listner){

        this.listner=listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

}
