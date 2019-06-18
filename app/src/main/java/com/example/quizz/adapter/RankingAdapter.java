package com.example.quizz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizz.R;
import com.example.quizz.model.Rank;

import org.w3c.dom.Text;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    Context mContext;
    List<Rank> mData;
    public RankingAdapter(Context mContext, List<Rank> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.ranking_item, viewGroup, false);
        RankingViewHolder rankingViewHolder = new RankingViewHolder(v);
        return rankingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder rankingViewHolder, int i) {
        rankingViewHolder.subject.setText("Môn học: "+mData.get(i).getSubjectName());
        rankingViewHolder.score.setText("Điểm số:" + mData.get(i).getScore());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class RankingViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ranking_item;
        private TextView subject;
        private TextView score;
        private TextView timeOfQuiz;
        public RankingViewHolder(View itemView){
            super(itemView);
            ranking_item = (LinearLayout)itemView.findViewById(R.id.ranking_item_id);
            subject = (TextView)itemView.findViewById(R.id.txt_subject_name);
            score = (TextView)itemView.findViewById(R.id.txt_score);
            timeOfQuiz = (TextView)itemView.findViewById(R.id.txt_time_quizz);
        }
    }
}
