package com.example.shoppingcart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcart.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Settings extends AppCompatActivity {

    private CircleImageView profileImageView;
    private TextView closeAcc,updateAcc,profilePic;
    private EditText etfname,etlname,etphone,etemail,etaddress;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;
    private String checker = "";

    private Button closeAccount,securityQuestion;

    AlertDialog.Builder alertBuilder;

    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile picture");

        loadingBar=new ProgressDialog(this);
        profileImageView = (CircleImageView) findViewById(R.id.user_profile_image);
        profilePic = findViewById(R.id.profile_pic);
        closeAcc = findViewById((R.id.close_settings));
        updateAcc = findViewById(R.id.update_settings);
        etfname = findViewById(R.id.acc_fname);
        etlname = findViewById(R.id.acc_lname);
        etphone = findViewById(R.id.acc_phone);
        etaddress = findViewById(R.id.acc_address);
        etemail = findViewById(R.id.acc_email);
        securityQuestion = findViewById(R.id.security_questions_btn);

        closeAccount=findViewById(R.id.close_accnt_btn);

        userInfoDisplay(profileImageView,etfname,etlname,etphone,etemail,etaddress);

        closeAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingBar.setTitle("Updating Account Details");
                loadingBar.setMessage("Please Wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                if (checker.equals("clicked"))
                {

                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(Settings.this);
            }
        });


        closeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteUser();
            }
        });


        securityQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,ResetPassword.class);
                intent.putExtra("check","Settings");
                startActivity(intent);
            }
        });


    }



    private void DeleteUser(){
        alertBuilder = new AlertDialog.Builder(Settings.this);
        alertBuilder.setTitle("Confirm Before Deleting Account");


        alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                DatabaseReference deleteUser = FirebaseDatabase.getInstance().getReference("Users")
                        .child(Prevalent.currentonlineUser.getPhone());

                DatabaseReference deleteCart = FirebaseDatabase.getInstance().getReference("Cart List")
                        .child("User View")
                        .child(Prevalent.currentonlineUser.getPhone());



                deleteUser.removeValue();
                deleteCart.removeValue();

                Toast.makeText(Settings.this, "Account Deleted Succesfully", Toast.LENGTH_SHORT).show();
                Paper.book().destroy();

                Intent login = new Intent(Settings.this, login.class);
                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
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


    }

    private void updateOnlyUserInfo()
    {

        if (TextUtils.isEmpty((etfname.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"First Name is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((etlname.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"Last Name is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((etphone.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"Phone number is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((etemail.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"Email is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((etaddress.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"Address is mandatory",Toast.LENGTH_SHORT).show();
        }
        else {


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

            HashMap<String, Object> userMAp = new HashMap<>();
            userMAp.put("FirstName", etfname.getText().toString());
            userMAp.put("LastName", etlname.getText().toString());
            userMAp.put("Phone", etphone.getText().toString());
            userMAp.put("Email", etemail.getText().toString());
            userMAp.put("Address", etaddress.getText().toString());
            ref.child(Prevalent.currentonlineUser.getPhone()).updateChildren(userMAp);

            loadingBar.dismiss();




            Intent intent = new Intent(Settings.this, Settings.class);
            startActivity(intent);

            Toast.makeText(Settings.this, "Profile Information updated successfully", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this,"Error,try again",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.this,Settings.class));
            finish();
        }
    }

    private void userInfoSaved()
    {
        if (TextUtils.isEmpty((etfname.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"First Name is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((etlname.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"Last Name is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((etphone.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"Phone number is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((etemail.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"Email is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((etaddress.getText().toString())))
        {
            loadingBar.dismiss();
            Toast.makeText(this,"Address is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (checker.equals("clicked"))
        {
            uploadImage();
        }
    }

    private void uploadImage() {
        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePictureRef.child(Prevalent.currentonlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful())
                    {
                        throw task.getException();

                    }

                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task <Uri>task) {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                                HashMap<String,Object> userMAp = new HashMap<>();
                                userMAp.put("FirstName",etfname.getText().toString());
                                userMAp.put("LastName",etlname.getText().toString());
                                userMAp.put("Phone",etphone.getText().toString());
                                userMAp.put("Email",etemail.getText().toString());
                                userMAp.put("Address",etaddress.getText().toString());
                                userMAp.put("Image",myUrl);
                                ref.child(Prevalent.currentonlineUser.getPhone()).updateChildren(userMAp);

                                loadingBar.dismiss();

                                Intent intent =  new Intent(Settings.this,Settings.class);

                                startActivity(intent);
                                Toast.makeText(Settings.this,"Profile Information updated successfully",Toast.LENGTH_SHORT).show();


                                finish();

                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(Settings.this,"Error",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else
        {
            loadingBar.dismiss();

            Toast.makeText(this,"Image not selected",Toast.LENGTH_SHORT).show();
        }

    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText etfname,final EditText etlname, final EditText etphone, final EditText etemail, final EditText etaddress) {

        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentonlineUser.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                     if (dataSnapshot.child("Image").exists())
                     {
                         String image = dataSnapshot.child("Image").getValue().toString();
                         String fname = dataSnapshot.child("FirstName").getValue().toString();
                         String lname = dataSnapshot.child("LastName").getValue().toString();
                         String phone = dataSnapshot.child("Phone").getValue().toString();
                         String email = dataSnapshot.child("Email").getValue().toString();
                         String address = dataSnapshot.child("Address").getValue().toString();




                         Picasso.get().load(image).into(profileImageView);
                         etfname.setText(fname);
                         etlname.setText(lname);
                         etphone.setText(phone);
                         etemail.setText(email);
                         etaddress.setText(address);
                     }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
