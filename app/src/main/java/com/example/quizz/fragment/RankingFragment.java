package com.example.quizz.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizz.R;
import com.example.quizz.adapter.RankingAdapter;
import com.example.quizz.model.Rank;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RankingFragment extends Fragment {
    View v;
    private RankingAdapter adapter;
    private RecyclerView rankingRecyclerView;
    private List<Rank> mData = new ArrayList<>();
    private DatabaseReference ref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        readScores();
        v = inflater.inflate(R.layout.fragment_ranking, container, false);
        rankingRecyclerView = (RecyclerView)v.findViewById(R.id.ranking_recyclerview);
        adapter = new RankingAdapter(getContext(), mData);
        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rankingRecyclerView.setAdapter(adapter);
        return v;
    }
    private void readScores(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("scores").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Rank r = snapshot.getValue(Rank.class);
                    mData.add(r);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
