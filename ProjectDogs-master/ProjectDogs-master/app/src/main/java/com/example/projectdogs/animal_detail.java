package com.example.projectdogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class animal_detail extends AppCompatActivity {

    ImageView Dogsimage;
    TextView name,age,found,description,type;
    Button Adopt;
    String DogName,DogFound,DogDescription,DogType,DogId, DImage;
    Long DogAge;
    //String idboby;
    //DatabaseReference database;
    int photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);
        name = findViewById(R.id.Namedetail);
        age = findViewById(R.id.Agedetail);
        found = findViewById(R.id.founddetail);
        description = findViewById(R.id.descriptiondetail);
        type = findViewById(R.id.Typedetail);
        Dogsimage = findViewById(R.id.DogsPhoto);
        Adopt= findViewById(R.id.AdoptButton);
        //database = FirebaseDatabase.getInstance().getReference("Dog");
        //idboby = database.child("Dog").getKey();
        Adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag",DogId);
                deleteDog(DogId);
                startActivity(new Intent(animal_detail.this,DogsListRecycle.class));            }
        });
        getData();
        setData();
    }

    private void deleteDog(String dogId) {
        DatabaseReference drDog = FirebaseDatabase.getInstance().getReference("Dog").child(dogId);
        drDog.removeValue();
        Toast.makeText(this,"dog will undergo adoption process",Toast.LENGTH_LONG).show();
    }

    private void getData(){
        if(getIntent().hasExtra("DImage")&&getIntent().hasExtra("DogId")&&getIntent().hasExtra("DogName")&&getIntent().hasExtra("DogType")
                && getIntent().hasExtra("DogAge")&& getIntent().hasExtra("DogFound")&& getIntent().hasExtra("DogDescription")){


            DogName = getIntent().getStringExtra("DogName");
            DogAge = getIntent().getLongExtra("DogAge",1);
            DogFound = getIntent().getStringExtra("DogFound");
            DogDescription = getIntent().getStringExtra("DogDescription");
            DogType = getIntent().getStringExtra("DogType");
            DogId = getIntent().getStringExtra("DogId");
            DImage = getIntent().getStringExtra("DImage");
            //zawedna el satr elly fo2ena dah 3la tool
            //            photo = getIntent().getIntExtra("Dog_image",1);

        }else{
            Toast.makeText(this, "No Data for this Dog.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setData(){
        name.setText(DogName);
        age.setText(DogAge.toString());
        found.setText(DogFound);
        description.setText(DogDescription);
        type.setText(DogType);
        Picasso.get().load(DImage).into(Dogsimage);
        //Dogsimage.setBackground(Drawable.createFromPath(DImage));
        //zawedna el satr dah
//        Dogsimage.setImageResource(photo);

    }
}