package com.example.projectdogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_an_account extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText FirstName, LastNAme, username, email,password,confirmpassword, phone;
Button create_Account;
DatabaseReference reff;
User user;
String  maxid;
FirebaseAuth fAuth;
FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_an_account);
        FirstName = (EditText) findViewById(R.id.FirstNameCreate);
//        LastNAme = (EditText) findViewById(R.id.LastNameCreate);
//        username = (EditText) findViewById(R.id.UsernameCreate);
        email = (EditText) findViewById(R.id.EmailCreate);
        password = (EditText) findViewById(R.id.PasswordCreate);
//        confirmpassword = (EditText) findViewById(R.id.ConfirmPasswordCreate);
        phone = (EditText) findViewById(R.id.PhoneCreate);
        create_Account =  (Button) findViewById(R.id.CreateAccount2);
        user = new User();
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
//        reff = FirebaseDatabase.getInstance().getReference().child("User");
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    //maxid = (snapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });


    }

    private void setSupportActionBar(Toolbar myToolbar) {
    }

    private void createUser(){
        String EMAIL = email.getText().toString().trim();
        String PASSWORD = password.getText().toString().trim();
        String FULLNAME = FirstName.getText().toString();
        String PHONE = phone.getText().toString();

        if(TextUtils.isEmpty(EMAIL)){
            email.setError("Email cannot be empty");
            email.requestFocus();
        } else if(TextUtils.isEmpty(PASSWORD)){
            password.setError("Password cannot be empty");
            password.requestFocus();
        }else if(PASSWORD.length() < 6){
            Toast.makeText(create_an_account.this, "Password must be greater than or equal to 6 characters!", Toast.LENGTH_SHORT).show();
        }
        else{
            fAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(create_an_account.this, "Account Created Successfully. Please check your Email for verification", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Toast.makeText(create_an_account.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                      /*  user.setFirstName(FirstName.getText().toString().trim());
                        user.setLastName(LastNAme.getText().toString().trim());
                        user.setUsername(username.getText().toString().trim());
                        user.setEmail(email.getText().toString().trim());
                        user.setPassword(password.getText().toString().trim());
                        Long telephone = Long.parseLong(phone.getText().toString().trim());
                        user.setPhone(telephone);*/
                        maxid = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("User").document(maxid);
                        Map<String,Object> users = new HashMap<>();
                        users.put("Full Name", FULLNAME);
                        users.put("Phone", PHONE);
                        users.put("Email", EMAIL);
                        users.put("Password", PASSWORD);
                        documentReference.set(users).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                                Log.d(TAG,"onSuccess: user profile is created for"+ maxid);}).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                            }
                        });
                        startActivity(new Intent(create_an_account.this, MainActivity.class));
                    }

                    else{
                        Toast.makeText(create_an_account.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tag","dah el error");

                    }
                }
            });
        }
    }
}