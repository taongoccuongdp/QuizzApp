package com.example.quizz.adapter;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz.R;
import com.example.quizz.model.Schedual;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class SchedualAdapter extends RecyclerView.Adapter<SchedualAdapter.SchedualViewHolder> {
    private Context mContex;
    private List<Schedual> mData;
    Dialog edtDialog;
    public SchedualAdapter(Context mContex, List<Schedual> mData) {
        this.mContex = mContex;
        this.mData = mData;
    }

    @NonNull
    @Override
    public SchedualViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContex).inflate(R.layout.schedual_content, viewGroup, false);
        final SchedualViewHolder holder = new SchedualViewHolder(v);
        edtDialog = new Dialog(mContex);
        edtDialog.setContentView(R.layout.dialog_edit_schedules);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edtNote = (EditText)edtDialog.findViewById(R.id.txt_edit_note);
                Button edtUpdate = (Button)edtDialog.findViewById(R.id.btn_update_note);
                edtNote.setText(mData.get(holder.getAdapterPosition()).getNote());
                edtDialog.show();
                edtUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference updateReference = FirebaseDatabase.getInstance().getReference("schedules").child(uid).child(mData.get(holder.getAdapterPosition()).getDate());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("note", edtNote.getText().toString());
                        updateReference.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(mContex, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                        edtDialog.dismiss();
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("schedules").child(uid).child(mData.get(holder.getAdapterPosition()).getDate());
                ref.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(mContex, "Xóa thành công", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SchedualViewHolder schedualViewHolder, int i) {
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
