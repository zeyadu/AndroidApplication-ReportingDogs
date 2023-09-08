package com.example.projectdogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class user_profile extends AppCompatActivity {
    TextView NameProfile,LastNameProfile,EmailProfile,passwordProfile,phoneprofile,usernameProfile;
    //DatabaseReference reff;
    FirebaseFirestore fstore;
    String maxid;
String full_name;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Button backHome=findViewById(R.id.homebutton);
        NameProfile = (TextView) findViewById(R.id.NameProfile);
//        LastNameProfile = (TextView) findViewById(R.id.LastNameProfile);
        EmailProfile = (TextView) findViewById(R.id.EmailProfile);
        passwordProfile = (TextView) findViewById(R.id.PasswordProfile);
        phoneprofile = (TextView) findViewById(R.id.phoneProfile);
//        usernameProfile =(TextView) findViewById(R.id.UsernameProfile);
        fAuth = FirebaseAuth.getInstance();
        maxid = fAuth.getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("User").document(maxid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       NameProfile.setText(documentSnapshot.getString("Full Name"));
                       EmailProfile.setText(documentSnapshot.getString("Email"));
                       passwordProfile.setText(documentSnapshot.getString("Password"));
                       phoneprofile.setText(documentSnapshot.getString("Phone"));
                        Log.d("akjhg","OnSuccess" + documentSnapshot.getData());
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.e("TAG","lkjhgfcd",e);
                            }
                        });
        /* reff = FirebaseDatabase.getInstance().getReference().child("User").child("-N6xc9WqdnckrQL8_T5I");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fname = snapshot.child("firstName").getValue().toString();
                String lname = snapshot.child("lastName").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String password = snapshot.child("password").getValue().toString();
                String username = snapshot.child("username").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();

                NameProfile.setText(fname);
                LastNameProfile.setText(lname);
                EmailProfile.setText(email);
                passwordProfile.setText(password);
                usernameProfile.setText(username);
                phoneprofile.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(user_profile.this,Home.class));
            }
        });
    }
}