package com.example.quizz.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quizz.LoginActivity;
import com.example.quizz.R;
import com.example.quizz.Uploadphoto;
import com.example.quizz.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    ImageView userAvatar;
    TextView phoneNumber;
    TextView studentId;
    TextView name;
    TextView email;
    Button signout;
    Button editProfile;
    private String userUid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userAvatar = (ImageView)view.findViewById(R.id.img_userAvatar);
        phoneNumber = (TextView)view.findViewById(R.id.txt_phoneNumber);
        studentId = (TextView)view.findViewById(R.id.txt_studentId);
        name = (TextView)view.findViewById(R.id.txt_username);
        email = (TextView)view.findViewById(R.id.txt_email);
        signout = (Button)view.findViewById(R.id.btn_signout);
        editProfile = (Button)view.findViewById(R.id.btn_editprofile);
        getUserProfile();
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Uploadphoto.class);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent signoutIntent = new Intent(getContext(), LoginActivity.class);
                startActivity(signoutIntent);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog edtProfile = new Dialog(getContext());
                edtProfile.setContentView(R.layout.dialog_edit_profile);
                final EditText edtName = (EditText)edtProfile.findViewById(R.id.txt_edit_name);
                final EditText edtPhone = (EditText)edtProfile.findViewById(R.id.txt_edit_phone);
                final EditText edtStudentId = (EditText)edtProfile.findViewById(R.id.txt_edit_studentid);
                Button update = (Button)edtProfile.findViewById(R.id.btn_update_profile);
                edtName.setText(name.getText().toString());
                edtPhone.setText(phoneNumber.getText().toString());
                edtStudentId.setText(studentId.getText().toString());
                edtProfile.show();
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference updateProfile = FirebaseDatabase.getInstance().getReference("users").child(userUid);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("fullname", edtName.getText().toString());
                        hashMap.put("phone", edtPhone.getText().toString());
                        hashMap.put("studentId", edtStudentId.getText().toString());
                        updateProfile.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                edtProfile.dismiss();
                            }
                        });
                    }
                });
            }
        });

        return view;
    }
    private void getUserProfile(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userUid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //ImageView
                Glide.with(getContext()).load(user.getAvatar()).into(userAvatar);
                studentId.setText(user.getStudentId());
                phoneNumber.setText(user.getPhone());
                email.setText(user.getEmail());
                name.setText(user.getFullname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
