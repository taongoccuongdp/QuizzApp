package com.example.quizz;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quizz.model.Subject;

import java.util.List;
import java.util.StringTokenizer;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {
    Context mContext;
    List<Subject> mData;
    //Dialog init
    Dialog mDialog;
    //TextView dialogSubjetName = (TextView)mDialog.findViewById(R.id.dialog_subject_name);
    //TextView dialogSubjetId = (TextView)mDialog.findViewById(R.id.dialog_subject_id);
    public HomeRecyclerViewAdapter(Context mContext, List<Subject> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item, viewGroup, false);
        HomeViewHolder viewHolder = new HomeViewHolder(v);
        //dialog click even listener
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_subject);
        viewHolder.home_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int i) {
        holder.subjectName.setText(mData.get(i).getName());
        holder.subjectId.setText("ID: " + mData.get(i).getId().toUpperCase());
        //holder.subjectIcon.setImageURI(Uri.parse(mData.get(i).getIcon()));
        Glide.with(mContext).load(mData.get(i).getIcon()).into(holder.subjectIcon);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        //Home view holder
        private LinearLayout home_item;
        private ImageView subjectIcon;
        private TextView subjectName;
        private TextView subjectId;
        private ImageView subjectMenu;
        public HomeViewHolder(View itemView){
            super(itemView);
            home_item = (LinearLayout)itemView.findViewById(R.id.home_item_id);
            subjectIcon = (ImageView)itemView.findViewById(R.id.img_subject);
            subjectName = (TextView)itemView.findViewById(R.id.txt_subject_name);
            subjectId = (TextView) itemView.findViewById(R.id.txt_subject_id);
            subjectMenu = (ImageView)itemView.findViewById(R.id.img_subject_menu);
        }
    }
}
