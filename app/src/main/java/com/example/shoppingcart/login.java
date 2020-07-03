package com.example.shoppingcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.annotation.NonNull;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.shoppingcart.Model.Users;
import com.example.shoppingcart.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;

    private TextView newaccnt;
    private TextView admin_tv;

    private EditText emailtv,passtv;

    private Button log_user;

    private FirebaseAuth firebaseAuth;

    AwesomeValidation awesomeValidation;



    private String parentDbname="Users";

    private CheckBox remmebr;

    private TextView forgetPasswordLink;

    private TextView admin,user;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().setTitle("Login");

        loadingBar=new ProgressDialog(this);
        textInputEmail=findViewById(R.id.text_input_email);
        textInputPassword=findViewById((R.id.text_input_password));
        newaccnt=findViewById(R.id.new_accnt_tv);
        admin_tv=findViewById(R.id.admin_tv);
        emailtv=findViewById(R.id.emtv);
        passtv=findViewById(R.id.pwtv);

        forgetPasswordLink = findViewById(R.id.forgetPasswordLink);

        remmebr=findViewById(R.id.remembermechekbox);
        Paper.init(this);

        log_user=findViewById(R.id.btnlog);

        admin=findViewById(R.id.admin_tv);
        user=findViewById(R.id.LinkForUser);

        user.setVisibility(View.INVISIBLE);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        newaccnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTosignup();
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();


        View decorView=getWindow().getDecorView();

        int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN ;

        decorView.setSystemUiVisibility(uiOptions);


        admin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Log in as Admin", Toast.LENGTH_SHORT).show();
                gotoaddproduct();
            }
        });

        log_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
                validatePassword();
                confirmInput();
            }

        });


        String UserPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPhoneKey != "" && userPasswordKey != ""){
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(userPasswordKey)){
                AllowAccess(UserPhoneKey,userPasswordKey);

                Toast.makeText(getApplicationContext(),"Already Logged In",Toast.LENGTH_SHORT).show();
            }
        }


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   log_user.setText("Login Admin");
                   admin.setVisibility(v.INVISIBLE);
                   user.setVisibility(v.VISIBLE);
                   parentDbname = "Admins";

            }


        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_user.setText("Login");
                admin.setVisibility(View.VISIBLE);
                user.setVisibility(View.INVISIBLE);
                parentDbname = "Users";
            }
        });


        forgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,ResetPassword.class);
                intent.putExtra("check","login");
                startActivity(intent);
            }
        });

    }

    private void AllowAccess(final String phone, final String pw) {

        final DatabaseReference RootRef;

        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadingBar.dismiss();
                if(dataSnapshot.child(("Users")).child(phone).exists()){
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone)){
                        if(usersData.getPassword().equals(pw)){

                            Toast.makeText(getApplicationContext(),"Login succesful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this,userprofile.class);
                            Prevalent.currentonlineUser = usersData;
                            System.out.println(Prevalent.currentonlineUser);

                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Invalid Password",Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"account with this " +phone+" Do not exist",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Create a New Account ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void gotoaddproduct(){
        Intent admin_addproduct = new Intent(this,AddFinishedProducts.class);
        startActivity(admin_addproduct);
    }


     private boolean validateEmail(){
        String emailInput=textInputEmail.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()){
            textInputEmail.setError("Field can't be empty");
            return false;
        }else{
            textInputEmail.setError(null);
            return true;
        }

    }

    private boolean validatePassword(){
        String passwordInput=textInputPassword.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()){
            textInputPassword.setError("Field can't be empty");
            return false;
        }else{
            textInputPassword.setError(null);
            return true;
        }

    }


    public void confirmInput(){

        if(validateEmail() | validatePassword()) {

            String phone = emailtv.getText().toString().trim();
            String pw = passtv.getText().toString().trim();

            loadingBar.setTitle("Logging In");
            loadingBar.setMessage("Please Wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();



            AllowAccessToAccount(phone, pw);

        }
    }


    public void goTosignup(){
        Intent gotoSignup = new Intent (this ,signup.class);
        startActivity(gotoSignup);
    }








    private void AllowAccessToAccount(final String phone, final String pw) {

        if(remmebr.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,pw);
        }
        final DatabaseReference RootRef;

        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadingBar.dismiss();

                if(dataSnapshot.child((parentDbname)).child(phone).exists()){
                    Users usersData = dataSnapshot.child(parentDbname).child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone)){

                        if(usersData.getPassword().equals(pw)) {

                            if (parentDbname.equals("Admins")) {
                                Toast.makeText(getApplicationContext(), "Admin-Login succesful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, AdminActivity.class);
                                startActivity(intent);
                            } else if(parentDbname.equals("Users")) {
                                Toast.makeText(getApplicationContext(), "Login succesful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(login.this, userprofile.class);
                                Prevalent.currentonlineUser = usersData;
                                startActivity(intent);
                            }


                        }else{
                            Toast.makeText(getApplicationContext(),"Invalid Password",Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"account with this " +phone+" Do not exist",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Create a New Account ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
