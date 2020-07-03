package com.example.shoppingcart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcart.Model.Company;
import com.example.shoppingcart.R;
import com.example.shoppingcart.ViewHolder.CompanyViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyProfile extends AppCompatActivity {

    private DatabaseReference CompanyRef;
    private TextView disName, disCity, disEmail, disPhone, disFax, disType,btnUpdate,btnDelete;
    private String companyPhone = "";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent companyList = new Intent(CompanyProfile.this,CompanyList.class);
        startActivity(companyList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);


        CompanyRef= FirebaseDatabase.getInstance().getReference().child("Company");

        companyPhone=getIntent().getStringExtra("phone");
        disName = findViewById(R.id.disCompany_name);
        disCity = findViewById(R.id.disCompany_city);
        disEmail = findViewById(R.id.disEmail);
        disPhone = findViewById(R.id.disPhone);
        disFax = findViewById(R.id.disCompFax);
        disType = findViewById(R.id.disType);
        btnUpdate=findViewById(R.id.comUpdate);
        btnDelete=findViewById(R.id.compDelete);

        getCompanyDetails(companyPhone);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent updateComp = new Intent(CompanyProfile.this,UpdateCompany.class);
                updateComp.putExtra("phone",companyPhone);
                startActivity(updateComp);

            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CompanyRef= FirebaseDatabase.getInstance().getReference().child("Company").child(companyPhone);
                CompanyRef.removeValue();

                Toast.makeText(CompanyProfile.this,"Company is Deleted",Toast.LENGTH_SHORT).show();
                Intent companyList = new Intent(CompanyProfile.this,CompanyList.class);
                startActivity(companyList);
            }
        });

    }



    private void getCompanyDetails(String CompanyPhone) {
        DatabaseReference CompanyRef = FirebaseDatabase.getInstance().getReference().child("Company");

        CompanyRef.child(CompanyPhone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Company company = dataSnapshot.getValue(Company.class);

                    disName.setText(company.getCompanyName());
                    disCity.setText(company.getCity());
                    disEmail.setText(company.getEmail());
                    disPhone.setText(company.getPhone());
                    disFax.setText(company.getFax());
                    disType.setText(company.getType());



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}


