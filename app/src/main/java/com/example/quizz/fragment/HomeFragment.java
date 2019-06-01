package com.example.quizz.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizz.adapter.HomeRecyclerViewAdapter;
import com.example.quizz.R;
import com.example.quizz.model.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    View v;
    HomeRecyclerViewAdapter adapter;
    private RecyclerView homeRecyclerView;
    private List<Subject> lstSubject = new ArrayList<>();
    DatabaseReference ref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readSubjects();
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        homeRecyclerView = (RecyclerView)v.findViewById(R.id.home_recyclerview);
        adapter = new HomeRecyclerViewAdapter(getContext(), lstSubject);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRecyclerView.setAdapter(adapter);
        return v;
    }
    private void readSubjects(){
        //read subjects list
        ref = FirebaseDatabase.getInstance().getReference("subjects");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstSubject.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Subject subject = snapshot.getValue(Subject.class);
                    lstSubject.add(subject);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
