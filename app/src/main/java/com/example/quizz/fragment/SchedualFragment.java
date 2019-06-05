package com.example.quizz.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.quizz.R;
import com.example.quizz.adapter.SchedualAdapter;
import com.example.quizz.model.Schedual;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SchedualFragment extends Fragment {
    View v;
    SchedualAdapter adapter;
    private List<Schedual> lstSchedual = new ArrayList<>();
    private RecyclerView schedualRecyclerView;
    DatabaseReference ref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        readSchedual();
        View view = inflater.inflate(R.layout.fragment_schedual, container, false);
        schedualRecyclerView = view.findViewById(R.id.schedual_recyclerview);
        schedualRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        schedualRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SchedualAdapter(getContext(), lstSchedual);
        schedualRecyclerView.setAdapter(adapter);
        return view;
    }
    private void readSchedual(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("schedules").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstSchedual.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Schedual schedual = snapshot.getValue(Schedual.class);
                    lstSchedual.add(schedual);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
