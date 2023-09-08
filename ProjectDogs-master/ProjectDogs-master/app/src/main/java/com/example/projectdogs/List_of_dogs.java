package com.example.projectdogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class List_of_dogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_dogs);
        ImageView bondo2 = findViewById(R.id.Bondo2);
        Button profile=findViewById(R.id.profile_list);
        Button report=findViewById(R.id.report);

        bondo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(List_of_dogs.this,animal_detail.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(List_of_dogs.this,user_profile.class));
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(List_of_dogs.this,report.class));
            }
        });
    }
}