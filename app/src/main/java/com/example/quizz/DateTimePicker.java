package com.example.quizz;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DateTimePicker extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText note;
    private EditText time;
    private Button add;
    private Button cancel;
    private int year;
    private int month;
    private int dayOfMonth;
    private int hour;
    private int minute;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);
        note = (EditText)findViewById(R.id.edt_note);
        time = (EditText)findViewById(R.id.edt_time);
        add = (Button) findViewById(R.id.btn_add);
        cancel = (Button) findViewById(R.id.btn_cancel);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year, month, dayOfMonth;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd =  new DatePickerDialog(DateTimePicker.this, DateTimePicker.this, year, month, dayOfMonth);
                dpd.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(note.getText().equals("")){
                    Toast.makeText(DateTimePicker.this, "Mục tiêu không được để trống", Toast.LENGTH_SHORT);
                }else if(time.getText().equals("")){
                    Toast.makeText(DateTimePicker.this, "Vui lòng chọn thời gian", Toast.LENGTH_SHORT);
                }else{
                    addToFirebase();
                    Intent schedualIntent = new Intent(DateTimePicker.this, MainActivity.class);
                    schedualIntent.putExtra("fragment switch", "schedual");
                    startActivity(schedualIntent);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schedualIntent = new Intent(DateTimePicker.this, MainActivity.class);
                schedualIntent.putExtra("fragment switch", "schedual");
                startActivity(schedualIntent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        int hour, minute;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(DateTimePicker.this, DateTimePicker.this, hour, minute, true);
        tpd.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        this.date = ExtendFunc.DateToString(this.year, this.month, this.dayOfMonth, this.hour, this.minute);
        time.setText(this.date);
    }
    private void addToFirebase(){
        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("schedules").child(userUID).child(this.date);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("date", this.date);
        hashMap.put("note", note.getText().toString());
        ref.setValue(hashMap);
    }
}
