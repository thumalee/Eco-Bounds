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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class signup extends AppCompatActivity {


        EditText fname,lname,email,phone,pass,cnfpass,textFname,textLname,textphone,textEmail,textPassword;
        Button submit;
        AwesomeValidation awesomeValidation;
        DatabaseReference dbRef;
        TextView alreadyMem;


        private ProgressBar progressBar;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        loadingBar=new ProgressDialog(this);

        alreadyMem=findViewById(R.id.already_a_mem_tv);
        fname=findViewById(R.id.fnametv);
        lname=findViewById(R.id.lnametv);
        email=findViewById(R.id.emailtv);
        phone=findViewById(R.id.mobnumtv);
        pass=findViewById(R.id.passwordtv);
        cnfpass=findViewById(R.id.confpasstv);
//       progressBar = findViewById(R.id.progressBar);
//       progressBar.setVisibility(View.GONE);

        submit=findViewById(R.id.new_accnt_btn);




        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //updateUI();



        View decorView=getWindow().getDecorView();

        int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN ;

        decorView.setSystemUiVisibility(uiOptions);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();

            }
        });

        alreadyMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this,login.class);
                startActivity(intent);
            }
        });

    }






    public void updateUI() {

            String firstname = fname.getText().toString();
            String lastname = lname.getText().toString();
            String phones = phone.getText().toString();
            String emails = email.getText().toString();
            String passwords = pass.getText().toString();

            if(TextUtils.isEmpty(firstname)){
                loadingBar.dismiss();
                Toast.makeText(this, "First Name is Mandatory", Toast.LENGTH_SHORT).show();
            }
            else
            if(TextUtils.isEmpty(lastname)){
                loadingBar.dismiss();
                Toast.makeText(this, "Last Name is Mandatory", Toast.LENGTH_SHORT).show();
            }
            else
            if(TextUtils.isEmpty(phones)){
                loadingBar.dismiss();
                Toast.makeText(this, "Phone Number is Mandatory", Toast.LENGTH_SHORT).show();
            }
            else
            if(TextUtils.isEmpty(emails)){
                loadingBar.dismiss();
                Toast.makeText(this, "Email is Mandatory", Toast.LENGTH_SHORT).show();
            }
            else
            if(TextUtils.isEmpty(passwords)){
                loadingBar.dismiss();
                Toast.makeText(this, "Password is Mandatory", Toast.LENGTH_SHORT).show();
            }
            else{
                registeruser();
            }


//        awesomeValidation.addValidation(signup.this, R.id.fnametv, "[a-zA-Z\\s]+", R.string.valfname);
//            awesomeValidation.addValidation(signup.this, R.id.lnametv, "[a-zA-Z\\s]+", R.string.vallname);
//            awesomeValidation.addValidation(signup.this, R.id.emailtv, android.util.Patterns.EMAIL_ADDRESS, R.string.valemail);
//            awesomeValidation.addValidation(signup.this, R.id.mobnumtv, RegexTemplate.TELEPHONE, R.string.valphone);
//
//            awesomeValidation.addValidation(signup.this, R.id.confpasstv, R.id.passwordtv, R.string.valconfpass);

    }




    public void registeruser(){

         //String finame=fname.getText().toString();
        String firstName= fname.getText().toString();
        String lastName=lname.getText().toString();
        //String liname=lname.getText().toString();
        String em = email.getText().toString();
          String Mobnum=phone.getText().toString();
         String pw = pass.getText().toString();



        loadingBar.setTitle("Creating a new User");
        loadingBar.setMessage("Please Wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();



        validatePhoneNum(firstName,lastName,em,Mobnum,pw);

}

    private void validatePhoneNum(final String Fname, final String Lname, final String em, final String Mobnum, final String pw) {

        final DatabaseReference RootRef;

        RootRef=FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(Mobnum).exists())){

                    HashMap<String,Object> userdataMap= new HashMap<>();
                    userdataMap.put("Email",em);
                    userdataMap.put("password",pw);
                    userdataMap.put("FirstName",Fname);
                    userdataMap.put("LastName",Lname);
                    userdataMap.put("Phone",Mobnum);

                    RootRef.child("Users").child(Mobnum).updateChildren(userdataMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            loadingBar.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(signup.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),login.class);
                                       startActivity(intent);
                            }
                            else{
                                Toast.makeText(signup.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(signup.this,"This "+ Mobnum + "already exists",Toast.LENGTH_SHORT).show();

                    Toast.makeText(signup.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
