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

import com.example.shoppingcart.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText fnameEditText,phoneEditText,addressEditText,cityEditText,lnameEditText;
    private Button confirmOrderButton;


    private String totalAmount ="";

    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        loadingBar=new ProgressDialog(this);
        confirmOrderButton=findViewById(R.id.confrim_btn_finalOrder);
        fnameEditText=findViewById(R.id.confirmorder_shipmentfName);
        lnameEditText=findViewById(R.id.confirmorder_shipmentlName);
        phoneEditText=findViewById(R.id.confirmorder_shipment_phone_num);
        addressEditText=findViewById(R.id.confirmorder_shipment_address);
        cityEditText=findViewById(R.id.confirmorder_shipment_address_city);


        totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this,"Total Price = Rs "+totalAmount,Toast.LENGTH_SHORT).show();



        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setTitle("Placing Your Order");
                loadingBar.setMessage("Please Wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                Check();
            }
        });


    }

    private void Check() {
        if(TextUtils.isEmpty(fnameEditText.getText().toString())){
            loadingBar.dismiss();
            Toast.makeText(this, "Please provide your First Name ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(lnameEditText.getText().toString())){
            loadingBar.dismiss();
            Toast.makeText(this, "Please provide your Last Name ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            loadingBar.dismiss();
            Toast.makeText(this, "Please provide your Phone Number ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            loadingBar.dismiss();
            Toast.makeText(this, "Please provide your Home Address ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString())){
            loadingBar.dismiss();
            Toast.makeText(this, "Please provide your City Name", Toast.LENGTH_SHORT).show();
        }
        else{
            ConfirmOrder();
        }

    }

    private void ConfirmOrder() {

        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentonlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();

        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("FirstName", fnameEditText.getText().toString());
        ordersMap.put("LastName", lnameEditText.getText().toString());
        ordersMap.put("Phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());

        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    FirebaseDatabase.getInstance().getReference().
                            child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentonlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your Order has Been Placed Succesfully", Toast.LENGTH_SHORT).show();


                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this, userprofile.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                }
            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ConfirmFinalOrderActivity.this,PaymentActivity.class);
        startActivity(intent);
    }
}
