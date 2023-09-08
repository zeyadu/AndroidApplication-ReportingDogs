package com.example.projectdogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> /*implements Filterable*/ {

    Context context;
    StorageReference DStorage;

    ArrayList<Dog> list;
   // ArrayList<Dog> dogsArrayList;

    public MyAdapter(Context context, ArrayList<Dog> list) {
        this.context = context;
        this.list = list;
       // this.dogsArrayList = new ArrayList<>(list);

    }
    public void setFilteredList(ArrayList<Dog> filo){
        this.list=filo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Dog dog = list.get(position);
        holder.DogName.setText(dog.getDogName());
        holder.DogType.setText(dog.getDogType());

        String imageURI = null;
        imageURI = dog.getDImage();
        Picasso.get().load(imageURI).into(holder.DImage);

        holder.dogcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,animal_detail.class);
                intent.putExtra("DogName",dog.getDogName());
                intent.putExtra("DogType",dog.getDogType());
                intent.putExtra("DogFound",dog.getDogFound());
                intent.putExtra("DogDescription",dog.getDogDescription());
                intent.putExtra("DogAge",dog.getDogAge());
                intent.putExtra("DogId",dog.getDogId());
                intent.putExtra("DImage",dog.getDImage());
                //zawedna a5er intent
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    @Override
//    public Filter getFilter() {
//        return null;
//    }
//
//    private final Filter dogsfilter = new Filter() {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//
//            ArrayList<Dog> FilteredDogsList = new ArrayList<>();
//
//            if (charSequence == null || charSequence.length() == 0) {
//                FilteredDogsList.addAll(list);
//            } else {
//
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//                for(Dog dog : list){
//
//                    if(dog.getDogType().toLowerCase().contains(filterPattern) || dog.getDogAge().toString().toLowerCase().contains(filterPattern))
//                        FilteredDogsList.add(dog);
//
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = FilteredDogsList;
//            results.count = FilteredDogsList.size();
//            return results;
//        }
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//
//            dogsArrayList.clear();
//            dogsArrayList.addAll((ArrayList)filterResults.values);
//            notifyDataSetChanged();
//        }
//    };

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView DogName, DogType;
        ImageView DImage;
        CardView dogcardview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            DogName = itemView.findViewById(R.id.DogsNameINRecycle);
            DogType = itemView.findViewById(R.id.DogsTypeINRecycle);
            dogcardview = itemView.findViewById(R.id.dogcardview);
            DImage = itemView.findViewById(R.id.DogsImageINRecycle);
//zawedna Dimage
        }
    }
}
