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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public void onBindViewHolder(@NonNull final SchedualViewHolder schedualViewHolder, int i) {
        schedualViewHolder.date.setText(mData.get(i).getDate().replace("-", "/"));
        schedualViewHolder.note.setText(mData.get(i).getNote());
        schedualViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("schedules").child(uid).child(schedualViewHolder.date.getText()
                        .toString().replace("/", "-"));
                ref.removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SchedualViewHolder extends RecyclerView.ViewHolder{
        private ImageView status;
        private TextView date;
        private TextView note;
        private ImageView delete;
        private ImageView edit;
        public SchedualViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.txtDate);
            note = (TextView)itemView.findViewById(R.id.txtNote);
            delete = (ImageView)itemView.findViewById(R.id.img_delete);
            edit = (ImageView)itemView.findViewById(R.id.img_edit);
        }
    }
}
