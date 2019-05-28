package com.example.quizz;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizz.model.Subject;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {
    Context mContext;
    List<Subject> mData;

    public HomeRecyclerViewAdapter(Context mContext, List<Subject> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item, viewGroup, false);
        HomeViewHolder viewHolder = new HomeViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int i) {
        holder.subjectName.setText(mData.get(i).getName());
        holder.subjectId.setText("Id: " + mData.get(i).getId());
        holder.subjectIcon.setImageURI(Uri.parse(mData.get(i).getIcon()));
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        private ImageView subjectIcon;
        private TextView subjectName;
        private TextView subjectId;
        private ImageView subjectMenu;
        public HomeViewHolder(View itemView){
            super(itemView);
            subjectIcon = (ImageView)itemView.findViewById(R.id.img_subject);
            subjectName = (TextView)itemView.findViewById(R.id.txt_subject_name);
            subjectId = (TextView) itemView.findViewById(R.id.txt_subject_id);
            subjectMenu = (ImageView)itemView.findViewById(R.id.img_subject_menu);
        }
    }
}
