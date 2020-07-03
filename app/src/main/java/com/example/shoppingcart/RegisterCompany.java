package com.example.shoppingcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterCompany extends AppCompatActivity {


    private Button btnRegister;
    private EditText companyName, companyCity,companyEmail,companyPhone,companyFax,companyType;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        btnRegister=(Button)findViewById(R.id.btnRegister);

        loadingBar=new ProgressDialog(this);
        companyName=(EditText)findViewById(R.id.companyName);
        companyCity=(EditText)findViewById(R.id.companyCity);
        companyEmail=(EditText)findViewById(R.id.companyEmail);
        companyPhone=(EditText)findViewById(R.id.companyPhone);
        companyType=(EditText)findViewById(R.id.companyType);
        companyFax=(EditText)findViewById(R.id.companyFaxNum);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount();
            }
        });
    }


    private void CreateAccount(){


        String name=companyName.getText().toString();
        String city=companyCity.getText().toString();
        String email=companyEmail.getText().toString();
        String phone=companyPhone.getText().toString();
        String fax=companyFax.getText().toString();
        String type=companyType.getText().toString();


        if(TextUtils.isEmpty(name)){

            Toast.makeText(this,"Please Insert Company Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(city)){

            Toast.makeText(this,"Please Insert City",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"Please Insert email",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){

            Toast.makeText(this,"Please Insert Company Phone number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(type)){

            Toast.makeText(this,"Please Insert the company type",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(fax)){

            Toast.makeText(this,"Please Insert Company Fax Number",Toast.LENGTH_SHORT).show();
        }

        else{

            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Validate(name,city,email,phone,type,fax);

        }
    }

    private void Validate(final String name, final String city, final String email, final String phone,final String type, final String fax) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Company").child(phone).exists())){

                    HashMap<String,Object> companydataMap=new HashMap<>();
                    companydataMap.put("companyName",name);
                    companydataMap.put("city",city);
                    companydataMap.put("email",email);
                    companydataMap.put("phone",phone);
                    companydataMap.put("fax",fax);
                    companydataMap.put("type",type);


                    RootRef.child("Company").child(phone).updateChildren(companydataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(RegisterCompany.this,"Account has been created",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        //sent to company profile
                                        Intent companyList=new Intent(RegisterCompany.this,CompanyList.class);
                                        startActivity(companyList);

                                    }
                                    else{

                                        Toast.makeText(RegisterCompany.this,"Error! Please try again..",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });


                }
                else{

                    Toast.makeText(RegisterCompany.this,"This "+phone+" already Exist",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterCompany.this,"Please try again using another email",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(RegisterCompany.this,CompanyList.class);
        startActivity(intent);

    }
}