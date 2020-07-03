package com.example.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

public class PaymentActivity extends AppCompatActivity {


    private String totalAmount ="";
    private Button gotoconfirmOrder;

    CardForm cardForm;
    AlertDialog.Builder alertBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        cardForm = findViewById(R.id.card_form);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .setup(PaymentActivity.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);








        totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this,"Total Price = Rs "+totalAmount,Toast.LENGTH_SHORT).show();

        gotoconfirmOrder=findViewById(R.id.goto_confirmOrder);

        gotoconfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentCheck();

            }
        });




    }


    public void paymentCheck(){
        if(cardForm.isValid()){
            alertBuilder = new AlertDialog.Builder(PaymentActivity.this);
            alertBuilder.setTitle("Confirm Payment Before Finalizing");
            alertBuilder.setMessage("Card number: " +cardForm.getCardNumber()+"\n" +
                    "Card expiry date: "+ cardForm.getExpirationDateEditText().getText().toString()+ "\n"+
                    "Card CVV: " +cardForm.getCvv() + "\n" +
                    "Postal code: " +cardForm.getPostalCode() + "\n" +
                    "Phone Number: " +cardForm.getMobileNumber());
            alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    Toast.makeText(PaymentActivity.this, "Payment Successful;", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentActivity.this,ConfirmFinalOrderActivity.class);
                    intent.putExtra("Total Price",String.valueOf(totalAmount));
                    startActivity(intent);
                    finish();
                }
            });
            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        }else{
            Toast.makeText(this, "Please Complete the form", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(PaymentActivity.this,cartActivity.class);
        startActivity(intent);
    }
}
