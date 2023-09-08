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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email_edit, password_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.createAccount);
        Button button2 = findViewById(R.id.login);

        email_edit = (EditText) findViewById(R.id.email_login);
        password_edit = (EditText) findViewById(R.id.password_login);
        mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this,create_an_account.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginUser();
            }
        });
    }

    private void loginUser(){
        String EMAIL = email_edit.getText().toString();
        String PASSWORD = password_edit.getText().toString();

        if(TextUtils.isEmpty(EMAIL)){
            email_edit.setError("Email cannot be empty");
            email_edit.requestFocus();
        } else if(TextUtils.isEmpty(PASSWORD)){
            password_edit.setError("Password cannot be empty");
            password_edit.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if(mAuth.getCurrentUser().isEmailVerified()){
                            Toast.makeText(MainActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,DogsListRecycle.class));
                            Log.d("Taaaag","success verification");
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                            Log.d("Taaaag2","failed verification");
                        }


                    }else{
                        Toast.makeText(MainActivity.this, "Email or password is invalid", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}