package com.example.shoppingcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcart.Model.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateCompany extends AppCompatActivity {

    private String companyPhone = "";
    private DatabaseReference CompanyRef;
    private Button btnUpdate;
    private EditText etname,etcity,etemail,etfax;
    private TextView tvPhone,tvType;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent companyProf = new Intent(UpdateCompany.this,CompanyProfile.class);
        companyProf.putExtra("phone",companyPhone);
        startActivity(companyProf);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company);

        companyPhone=getIntent().getStringExtra("phone");
        CompanyRef= FirebaseDatabase.getInstance().getReference().child("Company").child(companyPhone);

        btnUpdate=findViewById(R.id.btnUpdate);

        etname=findViewById(R.id.upcompanyName);
        etcity=findViewById(R.id.upcompanyCity);
        etemail=findViewById(R.id.upcompanyEmail);
        etfax=findViewById(R.id.upcompanyFaxNum);
        tvPhone=findViewById(R.id.TvPhone);
        tvType=findViewById(R.id.TvType);

        CompanyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Company company = dataSnapshot.getValue(Company.class);

                    tvPhone.setText(company.getPhone());
                    tvType.setText(company.getType());
                    etname.setText(company.getCompanyName());
                    etcity.setText(company.getCity());
                    etemail.setText(company.getEmail());
                    etfax.setText(company.getFax());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=etname.getText().toString().trim();
                String city=etcity.getText().toString().trim();
                String email=etemail.getText().toString().trim();
                String fax=etfax.getText().toString().trim();
                String phone=tvPhone.getText().toString().trim();
                String type=tvType.getText().toString().trim();

                updateCompany(name,city,email,phone,type,fax);

                Intent companyProf = new Intent(UpdateCompany.this,CompanyProfile.class);
                companyProf.putExtra("phone",companyPhone);
                startActivity(companyProf);




            }
        });
    }

    boolean updateCompany(String name, String city, String email, String phone, String type, String fax){

        Company company=new Company(name,city,email,phone,type,fax);
        CompanyRef.setValue(company);
        Toast.makeText(this,"Company Details Updated",Toast.LENGTH_SHORT).show();

        return true;
    }


}

