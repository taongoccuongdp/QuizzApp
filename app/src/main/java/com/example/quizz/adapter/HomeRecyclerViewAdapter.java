package com.example.quizz.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quizz.PlayActivity;
import com.example.quizz.R;
import com.example.quizz.model.QuizzSession;
import com.example.quizz.model.Subject;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder> {
    Context mContext;
    List<Subject> mData;
    Dialog mDialog;
    public HomeRecyclerViewAdapter(Context mContext, List<Subject> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item, viewGroup, false);
        final HomeViewHolder viewHolder = new HomeViewHolder(v);
        //dialog init
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_subject);
        viewHolder.home_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog click even listener
                TextView dialogSubjetName = (TextView)mDialog.findViewById(R.id.dialog_subject_name);
                TextView dialogSubjetId = (TextView)mDialog.findViewById(R.id.dialog_subject_id);
                Button btnPlayQuizz = (Button)mDialog.findViewById(R.id.btn_play);
                Button btnPlayTest = (Button)mDialog.findViewById(R.id.btn_test);
                ImageView dialogSubjectIcon = (ImageView)mDialog.findViewById(R.id.img_subject_icon);
                dialogSubjetName.setText(mData.get(viewHolder.getAdapterPosition()).getName());
                dialogSubjetId.setText(mData.get(viewHolder.getAdapterPosition()).getId().toUpperCase());
                Glide.with(mContext).load(mData.get(viewHolder.getAdapterPosition()).getIcon()).into(dialogSubjectIcon);
                mDialog.show();
                btnPlayQuizz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuizzSession.resetSesstion();
                        Intent intent = new Intent(mContext, PlayActivity.class);
                        QuizzSession.SUBJECT_ID = mData.get(viewHolder.getAdapterPosition()).getId();
                        QuizzSession.QUIZZ_TIME = QuizzSession.DONT_SET_TIME;
                        QuizzSession.SUBJECT_NAME = mData.get(viewHolder.getAdapterPosition()).getName();
                        QuizzSession.NUM_OF_QUESTIONS = QuizzSession.ALL_QUESTIONS;
                        QuizzSession.QUIZZ_CATEGORY = QuizzSession.QUIZZ;
                        mContext.startActivity(intent);
                    }
                });
                btnPlayTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuizzSession.resetSesstion();
                        Intent intent = new Intent(mContext, PlayActivity.class);
                        QuizzSession.SUBJECT_ID = mData.get(viewHolder.getAdapterPosition()).getId();
                        QuizzSession.QUIZZ_TIME = QuizzSession.TEST_TIME;
                        QuizzSession.SUBJECT_NAME = mData.get(viewHolder.getAdapterPosition()).getName();
                        QuizzSession.NUM_OF_QUESTIONS = QuizzSession.NUM_QUESTIONS_FOR_TEST;
                        QuizzSession.QUIZZ_CATEGORY = QuizzSession.TEST;
                        mContext.startActivity(intent);
                    }
                });

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
        public HomeViewHolder(View itemView){
            super(itemView);
            home_item = (LinearLayout)itemView.findViewById(R.id.home_item_id);
            subjectIcon = (ImageView)itemView.findViewById(R.id.img_subject);
            subjectName = (TextView)itemView.findViewById(R.id.txt_subject_name);
            subjectId = (TextView) itemView.findViewById(R.id.txt_subject_id);
        }
    }
}
