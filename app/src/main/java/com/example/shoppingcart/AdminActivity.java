package com.example.shoppingcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {


    private Button loutBtn,checkOrderButton,addProductsBtn,centerAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        loutBtn=findViewById(R.id.admin_logout);
        checkOrderButton=findViewById(R.id.ViewOrders_btn);
        addProductsBtn=findViewById(R.id.Productadd_btn);
        centerAdd= findViewById(R.id.Centersadd_btn);

        loutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        checkOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminActivity.this,AdminNewOrdersActivity.class);

                startActivity(intent);

            }
        });

        addProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminActivity.this,AdminCategoryActivity.class);
                //intent.putExtra("Admins","Admins");
                startActivity(intent);
            }
        });

        centerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,CompanyList.class);

                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {

    }
}
