package com.example.quizz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizz.model.QuizzSession;
import com.example.quizz.model.Rank;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EndActivity extends AppCompatActivity {
    private TextView subjectName;
    private TextView subjectId;
    private TextView numOfRightAnswer;
    private TextView txtTime;
    private int timeToQuizz;
    private Button endGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent quizzIntent = getIntent();
        timeToQuizz = (int) quizzIntent.getIntExtra("totalquizztime", 0);
        initView();
        txtTime.setText(String.format("Thời gian làm bài: %d s", timeToQuizz));
        Log.d("QuizzTime", String.valueOf(timeToQuizz));
        subjectName.setText(new StringBuilder().append("Tên môn học: ").append(QuizzSession.SUBJECT_NAME).toString());
        subjectId.setText(new StringBuilder().append("Mã môn học: ").append(QuizzSession.SUBJECT_ID).toString());
        numOfRightAnswer.setText(String.format("Điểm số: %d / %d", QuizzSession.SCORE*10/QuizzSession.NUM_OF_QUESTIONS, 10));
        if(QuizzSession.QUIZZ_CATEGORY.equals(QuizzSession.TEST)){
            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("scores").child(uid).child(QuizzSession.SUBJECT_ID);
            final HashMap<String, Object> hashMap = new HashMap<>();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Rank currentRank = dataSnapshot.getValue(Rank.class);
                    if(currentRank != null){
                        if(currentRank.getScore() < QuizzSession.SCORE*10/QuizzSession.NUM_OF_QUESTIONS){
                            hashMap.put("score", QuizzSession.SCORE*10/QuizzSession.NUM_OF_QUESTIONS);
                        }
                        hashMap.put("numOfTest", currentRank.getNumOfTest() + 1);
                        FirebaseDatabase.getInstance().getReference("scores").child(uid).child(QuizzSession.SUBJECT_ID).updateChildren(hashMap);
                    }else {
                        hashMap.put("score", QuizzSession.SCORE*10/QuizzSession.NUM_OF_QUESTIONS);
                        hashMap.put("subjectName", QuizzSession.SUBJECT_NAME);
                        hashMap.put("subjectId", QuizzSession.SUBJECT_ID);
                        hashMap.put("numOfTest", 1);
                        FirebaseDatabase.getInstance().getReference("scores").child(uid).child(QuizzSession.SUBJECT_ID).setValue(hashMap);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initView(){
        subjectName = (TextView)findViewById(R.id.txt_subject_name);
        subjectId = (TextView)findViewById(R.id.txt_subject_id);
        numOfRightAnswer = (TextView)findViewById(R.id.txt_num_right_answers);
        txtTime = (TextView)findViewById(R.id.txt_time_quizz);
        endGame = (Button) findViewById(R.id.btn_endgame);
    }
}
