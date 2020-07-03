package com.example.shoppingcart;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.Model.Company;
import com.example.shoppingcart.ViewHolder.CompanyViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompanyList extends AppCompatActivity {

    private DatabaseReference CompanyRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        CompanyRef = FirebaseDatabase.getInstance().getReference().child("Company");

        addCompany = findViewById(R.id.btnAddCompany);

        recyclerView = findViewById(R.id.recycler_com);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        addCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegiComp = new Intent(CompanyList.this, RegisterCompany.class);
                startActivity(RegiComp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Company> options =
                new FirebaseRecyclerOptions.Builder<Company>()
                        .setQuery(CompanyRef, Company.class)
                        .build();

        FirebaseRecyclerAdapter<Company, CompanyViewHolder> adapter =
                new FirebaseRecyclerAdapter<Company, CompanyViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CompanyViewHolder holder, int position, @NonNull final Company model) {

                        holder.compName.setText("Company : " + model.getCompanyName());
                        holder.comPhone.setText("Phone : " + model.getPhone());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent companyProf = new Intent(CompanyList.this, CompanyProfile.class);
                                companyProf.putExtra("phone", model.getPhone());
                                startActivity(companyProf);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_item_layout, viewGroup, false);
                        CompanyViewHolder holder = new CompanyViewHolder(view);

                        return holder;

                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CompanyList.this,AdminActivity.class);
        startActivity(intent);
    }
}
