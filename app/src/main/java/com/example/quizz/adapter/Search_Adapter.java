package com.example.quizz.adapter;

import android.app.Dialog;
import android.content.Context;
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
import com.example.quizz.R;
import com.example.quizz.model.Questions;
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
                Button btnPlay = (Button)mDialog.findViewById(R.id.btn_play);
                dialogSubjetName.setText(mData.get(searchViewHolder.getAdapterPosition()).getName());
                dialogSubjetId.setText(mData.get(searchViewHolder.getAdapterPosition()).getId().toUpperCase());
                Glide.with(mContext).load(mData.get(searchViewHolder.getAdapterPosition()).getIcon()).into(dialogSubjectIcon);
                mDialog.show();
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
            subjectId = (TextView)itemView.findViewById(R.id.txt_subject_name);
            subjectIcon = (ImageView)itemView.findViewById(R.id.img_subject);
            homeItem = (LinearLayout)itemView.findViewById(R.id.home_item_id);
        }
    }
}
