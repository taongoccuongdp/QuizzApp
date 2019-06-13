package com.example.quizz;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz.fragment.ProfileFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;
import java.util.Map;

public class Uploadphoto extends AppCompatActivity {
    TextView txtUpload;
    ImageView imgUpload;
    StorageReference storageReference;
    StorageTask uploadTask;
    String myUrl;
    Uri imageUri;
    public static int SELECT_PICTURE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadphoto);
        myUrl = "";
        txtUpload = (TextView)findViewById(R.id.txt_upload_loading);
        imgUpload = (ImageView)findViewById(R.id.img_upload_icon);
        txtUpload.setVisibility(View.INVISIBLE);
        imgUpload.setVisibility(View.INVISIBLE);
        storageReference = FirebaseStorage.getInstance().getReference("users");
        getPhoto();
    }
    private void getPhoto(){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, SELECT_PICTURE);
    }
    private void uploadImage(){
        txtUpload.setVisibility(View.VISIBLE);
        imgUpload.setVisibility(View.VISIBLE);
        if(imageUri != null){
            final StorageReference fileReference = storageReference.child(new StringBuilder()
                    .append(getCurrentUser())
                    .append("_avatar.")
                    .append(getFileExtension(imageUri))
                    .toString());
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isComplete()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("avatar", myUrl);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(getCurrentUser());
                        ref.updateChildren(hashMap);
                        txtUpload.setVisibility(View.INVISIBLE);
                        imgUpload.setVisibility(View.INVISIBLE);
                        Intent profileIntent = new Intent(Uploadphoto.this, MainActivity.class);
                        startActivity(profileIntent);
                    }else{
                        Toast.makeText(Uploadphoto.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private String getCurrentUser(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser().getUid();
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == SELECT_PICTURE){
                imageUri = data.getData();
                uploadImage();
            }
        }
    }
}
