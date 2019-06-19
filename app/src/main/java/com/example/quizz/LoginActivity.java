package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edtemail, edtpassword;
    private FirebaseAuth auth;
    private Button login;
    private TextView signup;
    private ToggleButton remememberMe;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtemail = (EditText)findViewById(R.id.txtLogin_Email);
        edtpassword = (EditText)findViewById(R.id.txtLogin_Password);
        login = (Button)findViewById(R.id.btnLogin_Login);
        signup = (TextView)findViewById(R.id.txtLogin_Register);
        remememberMe = (ToggleButton) findViewById(R.id.rbutton_remember);
        sharedPreferences = getSharedPreferences("com.example.quizz", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        checkRemember();
        //Get firebase auth instance
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        remememberMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remememberMe.getText().equals(remememberMe.getTextOn())){
                    remememberMe.setText(remememberMe.getTextOff());
                }else{
                    remememberMe.setText(remememberMe.getTextOn());
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rMB();
                pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Đang đăng nhập ...");
                pd.show();
                String email = edtemail.getText().toString();
                final String password = edtpassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Nhập email!", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Nhập password!", Toast.LENGTH_SHORT).show();
                }
                //authenticate user
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Login failed
                        if(!task.isSuccessful()){
                            pd.dismiss();
                            if(password.length() < 6){
                                edtpassword.setError("Mật khẩu cần ít nhất là 6");
                            }else {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    pd.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
//
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    }
                });
            }
        });
    }
    private void checkRemember(){
        if (sharedPreferences.getBoolean("remember", true)){
            edtemail.setText(sharedPreferences.getString("email", ""));
            edtpassword.setText(sharedPreferences.getString("password", ""));
            remememberMe.setText(remememberMe.getTextOn());
        }else{
            remememberMe.setText(remememberMe.getTextOff());
        }
    }
    private void rMB(){

        if(remememberMe.getText().equals(remememberMe.getTextOn())){
            editor.putString("email", edtemail.getText().toString());
            editor.putString("password", edtpassword.getText().toString());
            editor.putBoolean("remember", true);
            editor.apply();
        }else{
            editor.putString("email", "");
            editor.putString("password", "");
            editor.putBoolean("remember", false);
            editor.apply();
        }
    }
}
