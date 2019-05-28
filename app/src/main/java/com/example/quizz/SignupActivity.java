package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private EditText edt_email, edt_password, edt_confirm_password, edt_studentId, edt_fullname, edt_phone;
    private Button btn_register;
    private TextView txt_login;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edt_email = (EditText)findViewById(R.id.txtRegister_Email);
        edt_password = (EditText)findViewById(R.id.txtRegister_Password);
        edt_confirm_password = (EditText)findViewById(R.id.txtRegister_ConfirmPassword);
        edt_studentId = (EditText)findViewById(R.id.txtRegister_StudentId);
        edt_fullname = (EditText)findViewById(R.id.txtRegister_Name);
        edt_phone = (EditText)findViewById(R.id.txtRegister_PhoneNumber);
        btn_register = (Button)findViewById(R.id.btnRegister_Register);
        txt_login = (TextView)findViewById(R.id.txtRegister_Login);
        auth = FirebaseAuth.getInstance();
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(SignupActivity.this);
                pd.setMessage("Vui lòng đợi giây lát ...");
                pd.show();
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                String confirmPassword = edt_confirm_password.getText().toString();
                String studentId = edt_studentId.getText().toString();
                String fullname = edt_fullname.getText().toString();
                String phone = edt_phone.getText().toString();
                User user = new User(email, password, studentId, phone, fullname);
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(studentId) || TextUtils.isEmpty(studentId) || TextUtils.isEmpty(fullname)||TextUtils.isEmpty(phone)){
                    Toast.makeText(SignupActivity.this, "Cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT);
                }else if (password.length() < 6){
                    Toast.makeText(SignupActivity.this, "Mật khẩu cần ít nhất 6 ký tự", Toast.LENGTH_SHORT);
                }else{
                    register(user);
                }
            }
        });
    }
    private void register(final User user){
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    user.setId(firebaseUser.getUid());
                    ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getId());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", user.getId());
                    hashMap.put("email", user.getEmail());
                    hashMap.put("password", user.getPassword());
                    hashMap.put("fullname", user.getFullname());
                    hashMap.put("phone", user.getPhone());
                    hashMap.put("studentId", user.getStudentId());
                    hashMap.put("avatar", user.getAvatar());
                    ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pd.dismiss();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(SignupActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
