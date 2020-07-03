package com.example.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppingcart.Model.Company;
import com.example.shoppingcart.ViewHolder.CompanyUserViewHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListCompany_User extends AppCompatActivity {


        private DatabaseReference CompanyRef;
        private RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

        AlertDialog.Builder alertBuilder;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_company__user);

            CompanyRef= FirebaseDatabase.getInstance().getReference().child("Company");

            recyclerView=findViewById(R.id.recycler_com_user);
            recyclerView.setHasFixedSize(true);
            layoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);



        }

        @Override
        protected void onStart(){
            super.onStart();

            FirebaseRecyclerOptions<Company> options =
                    new FirebaseRecyclerOptions.Builder<Company>()
                            .setQuery(CompanyRef, Company.class)
                            .build();

            FirebaseRecyclerAdapter<Company, CompanyUserViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Company, CompanyUserViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CompanyUserViewHolder holder, int position, @NonNull final Company model) {

                            holder.compName.setText("Company Name: "+model.getCompanyName());
                            holder.comCity.setText("City: "+model.getCity());





//                    holder.compName.setText(model.getCompanyName());
//                    holder.comPhone.setText(model.getPhone());
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertBuilder = new AlertDialog.Builder(ListCompany_User.this);
                                    alertBuilder.setTitle("Company Details");
                                    alertBuilder.setMessage("\nCompany Name: " +model.getCompanyName()+"\n\n" +
                                            "Phone Number: "+ model.getPhone()+ "\n\n"+
                                            "Collection Type: " +model.getType() + "\n\n" +
                                            "City: " +model.getCity() + "\n\n" +
                                            "Email: " +model.getEmail()+"\n\n" +
                                            "Fax: "+model.getFax());


                                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });

                                    AlertDialog alertDialog = alertBuilder.create();
                                    alertDialog.show();
                                }
                            });

                        }

                        @NonNull
                        @Override
                        public CompanyUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_user_view_layout,viewGroup,false);
                            CompanyUserViewHolder holder = new CompanyUserViewHolder(view);

                            return holder;

                        }
                    };

            recyclerView.setAdapter(adapter);
            adapter.startListening();

        }
    }

