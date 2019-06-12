package com.example.quizz.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import com.example.quizz.model.Questions;
import com.example.quizz.model.QuizzSession;
import com.example.quizz.model.Subject;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.util.List;

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.SearchViewHolder> {
    private Context mContext;
    private List<Subject> mData;
    Dialog mDialog;
    public Search_Adapter(Context mContext, List<Subject> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item, viewGroup, false);
        final SearchViewHolder searchViewHolder = new SearchViewHolder(v);
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_subject);
        searchViewHolder.homeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog click even listener
                TextView dialogSubjetName = (TextView)mDialog.findViewById(R.id.dialog_subject_name);
                TextView dialogSubjetId = (TextView)mDialog.findViewById(R.id.dialog_subject_id);
                ImageView dialogSubjectIcon = (ImageView)mDialog.findViewById(R.id.img_subject_icon);
                Button btnPlayQuizz = (Button)mDialog.findViewById(R.id.btn_play);
                Button btnPlayTest = (Button)mDialog.findViewById(R.id.btn_test);
                dialogSubjetName.setText(mData.get(searchViewHolder.getAdapterPosition()).getName());
                dialogSubjetId.setText(mData.get(searchViewHolder.getAdapterPosition()).getId().toUpperCase());
                Glide.with(mContext).load(mData.get(searchViewHolder.getAdapterPosition()).getIcon()).into(dialogSubjectIcon);
                mDialog.show();
                btnPlayQuizz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuizzSession.resetSesstion();
                        Intent intent = new Intent(mContext, PlayActivity.class);
                        QuizzSession.SUBJECT_ID = mData.get(searchViewHolder.getAdapterPosition()).getId();
                        QuizzSession.QUIZZ_TIME = QuizzSession.DONT_SET_TIME;
                        QuizzSession.SUBJECT_NAME = mData.get(searchViewHolder.getAdapterPosition()).getName();
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
                        QuizzSession.SUBJECT_ID = mData.get(searchViewHolder.getAdapterPosition()).getId();
                        QuizzSession.QUIZZ_TIME = QuizzSession.TEST_TIME;
                        QuizzSession.SUBJECT_NAME = mData.get(searchViewHolder.getAdapterPosition()).getName();
                        QuizzSession.NUM_OF_QUESTIONS = QuizzSession.NUM_QUESTIONS_FOR_TEST;
                        QuizzSession.QUIZZ_CATEGORY = QuizzSession.TEST;
                        mContext.startActivity(intent);
                    }
                });
            }
        });
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.subjectName.setText(mData.get(i).getName());
        searchViewHolder.subjectId.setText("ID: " + mData.get(i).getId().toUpperCase());
        //holder.subjectIcon.setImageURI(Uri.parse(mData.get(i).getIcon()));
        Glide.with(mContext).load(mData.get(i).getIcon()).into(searchViewHolder.subjectIcon);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        private TextView subjectName, subjectId;
        private ImageView subjectIcon;
        private LinearLayout homeItem;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = (TextView)itemView.findViewById(R.id.txt_subject_name);
            subjectId = (TextView)itemView.findViewById(R.id.txt_subject_id);
            subjectIcon = (ImageView)itemView.findViewById(R.id.img_subject);
            homeItem = (LinearLayout)itemView.findViewById(R.id.home_item_id);
        }
    }
}
