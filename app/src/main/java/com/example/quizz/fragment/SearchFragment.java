package com.example.quizz.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.quizz.R;
import com.example.quizz.adapter.Search_Adapter;
import com.example.quizz.model.Subject;
import com.example.quizz.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private Search_Adapter adapter;
    private RecyclerView searchRecyclerView;
    private List<Subject> mData;
    private DatabaseReference ref;
    EditText searchBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchRecyclerView = v.findViewById(R.id.search_recycler_view);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchBar = (EditText)v.findViewById(R.id.search_bar);
        mData = new ArrayList<>();
        adapter = new Search_Adapter(getContext(), mData);
        searchRecyclerView.setAdapter(adapter);
        readSubjects();
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSubject(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }
    private void searchSubject(String s){
        Query query = FirebaseDatabase.getInstance().getReference("subjects").orderByChild("id").startAt(s).endAt(s+"\tf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Subject s = snapshot.getValue(Subject.class);
                    mData.add(s);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readSubjects(){
        //read subjects list
        ref = FirebaseDatabase.getInstance().getReference("subjects");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Subject subject = snapshot.getValue(Subject.class);
                    mData.add(subject);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
