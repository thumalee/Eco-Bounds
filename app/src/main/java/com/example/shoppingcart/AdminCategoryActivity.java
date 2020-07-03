package com.example.shoppingcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView bags, phoneCovers, shoes, bottles;
    private ImageView jackets, tshirts, dresses, trousers;

    private Button maintainProductBtn;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        maintainProductBtn =  (Button) findViewById(R.id.maintain_product_btn);

        //getIntent().getExtras().get("Admins").toString();
        maintainProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,userprofile.class);
                intent.putExtra("Admins","Admins");
                startActivity(intent);
            }
        });


        bags = (ImageView) findViewById(R.id.i1);
        phoneCovers = (ImageView) findViewById(R.id.i2);
        shoes = (ImageView) findViewById(R.id.i3);
        bottles = (ImageView) findViewById(R.id.i4);
        jackets = (ImageView) findViewById(R.id.i5);
        tshirts = (ImageView) findViewById(R.id.i6);
        dresses = (ImageView) findViewById(R.id.i7);
        trousers = (ImageView) findViewById(R.id.i8);

        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AddFinishedProducts.class);
                intent.putExtra("category","Bags");
                startActivity(intent);

            }
        });

        phoneCovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AddFinishedProducts.class);
                intent.putExtra("category","Phone Covers");
                startActivity(intent);

            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AddFinishedProducts.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);

            }
        });

        bottles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AddFinishedProducts.class);
                intent.putExtra("category","Bottles");
                startActivity(intent);

            }
        });

        jackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AddFinishedProducts.class);
                intent.putExtra("category","Jackets");
                startActivity(intent);

            }
        });

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AddFinishedProducts.class);
                intent.putExtra("category","T-shirts");
                startActivity(intent);

            }
        });

        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AddFinishedProducts.class);
                intent.putExtra("category","Dresses");
                startActivity(intent);

            }
        });

        trousers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategoryActivity.this,AddFinishedProducts.class);
                intent.putExtra("category","Trousers");
                startActivity(intent);

            }
        });




    }

    @Override
    public void onBackPressed() {

    }
}
