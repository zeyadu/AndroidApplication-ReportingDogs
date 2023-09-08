package com.example.projectdogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DogsListRecycle extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Dog> list;
    ImageView wadeenyprofile;
    Button report;
    String idboby;
//    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list_recycle);

        wadeenyprofile = findViewById(R.id.takemetoprofile);

//        searchView = findViewById(R.id.searchView);
//        searchView.clearFocus();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
////                filterList(s);
//                return true;
//            }
//        });

        report =findViewById(R.id.Reportbutton);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DogsListRecycle.this,report.class));
            }
        });
        wadeenyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DogsListRecycle.this,user_profile.class));
            }
        });
        recyclerView = findViewById(R.id.dogList);
        database = FirebaseDatabase.getInstance().getReference("Dog");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        myAdapter  = new MyAdapter(DogsListRecycle.this, list);
        recyclerView.setAdapter(myAdapter);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Dog dog = dataSnapshot.getValue(Dog.class);
                    list.add(dog);
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void filterList(String s) {
//
//        ArrayList<Dog> filteredList = new ArrayList<>();
//        for (Dog dog : list){
//            if(dog.getDogType().toLowerCase().contains(s.toLowerCase())){
//                filteredList.add(dog);
//            }
//        }
//        if (filteredList.isEmpty()){
//            Toast.makeText(DogsListRecycle.this,"No data found",Toast.LENGTH_SHORT).show();
//        }else{
//            myAdapter.setFilteredList(filteredList);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_item,menu);
//        MenuItem menuItem = menu.findItem(R.id.search_action);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setQueryHint("Search for Type or Age of Dogs");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                myAdapter.getFilter().filter(s);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
}