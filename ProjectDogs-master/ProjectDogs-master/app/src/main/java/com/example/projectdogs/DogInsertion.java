package com.example.projectdogs;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class DogInsertion extends AppCompatActivity {

    ImageView DogInsertionPhoto;
    EditText DName, DAge, DType, DDescription, DFound;
    DatabaseReference reff;
    StorageReference DStorage;
    Button Dcreate, Dsora;
    Dog dog;
    private StorageTask uploadTask;
    public Uri imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_insertion);
        DStorage = FirebaseStorage.getInstance().getReference();
        DogInsertionPhoto = (ImageView) findViewById(R.id.DogInsertImage);
        DName = (EditText) findViewById(R.id.DogInsertName);
        DAge = (EditText) findViewById(R.id.DogInsertAge);
        DType = (EditText) findViewById(R.id.DogInsertType);
        DDescription = (EditText) findViewById(R.id.DogInsertDescription);
        DFound = (EditText) findViewById(R.id.DogInsertFound);
        Dcreate = (Button) findViewById(R.id.caryetdog);
        Dsora = (Button) findViewById(R.id.soretkalbak);
        dog = new Dog();

        reff = FirebaseDatabase.getInstance().getReference().child("Dog");
//        Dsora.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Filechooser();
//            }
//        });
        DogInsertionPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                someActivityResultLauncher.launch(galleryintent);
            }
        });


        Dcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long Age = Long.parseLong(DAge.getText().toString().trim());
                dog.setDogName(DName.getText().toString().trim());
                dog.setDogType(DType.getText().toString().trim());
                dog.setDogDescription(DDescription.getText().toString().trim());
                dog.setDogFound(DFound.getText().toString().trim());
                dog.setDogAge(Age);
              //  if(imguri != null){

                    String id = reff.push().getKey();
                    dog.setDogId(id);
                    uploadtoFirebase(imguri);
                    reff.child(id).setValue(dog);

                //}
                //else{
                  //  Toast.makeText(DogInsertion.this,"Please insert image",Toast.LENGTH_SHORT).show();
                //}
                //fileuploader();

//                reff.push().setValue(dog);
//                Toast.makeText(DogInsertion.this, "data inserted successfuly",Toast.LENGTH_LONG).show();
            }
        });

    }
//    private String getExtension(Uri uri){
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
//    }
//    private void fileuploader(){
//        String imageid;
//        imageid = System.currentTimeMillis()+"."+getExtension(imguri);
//        StorageReference Ref = DStorage.child(imageid);
//        dog.setDImage(imageid);
//        uploadTask = Ref.putFile(imguri).addOnSuccessListener((OnSuccessListener) (taskSnapshot) -> {
//            Toast.makeText(DogInsertion.this,"Image Uploaded Successfully",Toast.LENGTH_LONG).show();
//        }
//        ).addOnFailureListener((exception) -> {
//
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
//            imguri = data.getData();
//            DogInsertionPhoto.setImageURI(imguri);
//        }
//    }
//
//    private void Filechooser(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,1);
//    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult result){
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    imguri = data.getData();
                    DogInsertionPhoto.setImageURI(imguri);
                }
            }
        });
    private void uploadtoFirebase(Uri uri){

        StorageReference fileref = DStorage.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dog.setDImage(uri.toString());
                        Toast.makeText(DogInsertion.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DogInsertion.this,"Uploading Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri uris){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uris));
    }
}