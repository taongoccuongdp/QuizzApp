package com.example.quizz.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizz.HomeRecyclerViewAdapter;
import com.example.quizz.R;
import com.example.quizz.model.Subject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class HomeFragment extends Fragment {
    View v;
    private RecyclerView homeRecyclerView;
    private List<Subject> lstSubject;
    DatabaseReference ref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref = FirebaseDatabase.getInstance().getReference().child("subjects");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        homeRecyclerView = (RecyclerView)v.findViewById(R.id.home_recyclerview);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(), lstSubject);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRecyclerView.setAdapter(adapter);
        return v;
    }
}
