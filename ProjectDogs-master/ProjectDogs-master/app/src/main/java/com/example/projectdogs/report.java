package com.example.projectdogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class report extends AppCompatActivity {

    EditText TypeD,whereD,WhenD;
    reportForm report;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Button submit =findViewById(R.id.homebutton_aboutus);
        TypeD = (EditText) findViewById(R.id.editText2);
        whereD = (EditText) findViewById(R.id.editText);
        WhenD = (EditText) findViewById(R.id.editText3);
        report = new reportForm();
        reff =  reff = FirebaseDatabase.getInstance().getReference().child("Report");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                report.setTypeDog(TypeD.getText().toString().trim());
                report.setWhereDog(whereD.getText().toString().trim());
                report.setWhenDog(WhenD.getText().toString().trim());

                reff.push().setValue(report);
                Toast.makeText(report.this, "Dog is reported",Toast.LENGTH_LONG).show();
                startActivity(new Intent(report.this,DogsListRecycle.class));
            }
        });

    }
}