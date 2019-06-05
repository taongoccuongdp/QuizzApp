package com.example.quizz.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private String id;
    private String name;
    private String icon;

    public Subject(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }
    public Subject(){

    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getIcon() {
        return icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
