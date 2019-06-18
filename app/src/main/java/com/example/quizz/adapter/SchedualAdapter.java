package com.example.quizz.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizz.R;
import com.example.quizz.model.Schedual;

import java.util.List;

public class SchedualAdapter extends RecyclerView.Adapter<SchedualAdapter.SchedualViewHolder> {
    private Context mContex;
    private List<Schedual> mData;

    public SchedualAdapter(Context mContex, List<Schedual> mData) {
        this.mContex = mContex;
        this.mData = mData;
    }

    @NonNull
    @Override
    public SchedualViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContex).inflate(R.layout.schedual_content, viewGroup, false);
        final SchedualViewHolder holder = new SchedualViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SchedualViewHolder schedualViewHolder, int i) {
        schedualViewHolder.date.setText(mData.get(i).getDate().replace("-", "/"));
        schedualViewHolder.note.setText(mData.get(i).getNote());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SchedualViewHolder extends RecyclerView.ViewHolder{
        private ImageView status;
        private TextView date;
        private TextView note;
        public SchedualViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.txtDate);
            note = (TextView)itemView.findViewById(R.id.txtNote);
        }
    }
}
