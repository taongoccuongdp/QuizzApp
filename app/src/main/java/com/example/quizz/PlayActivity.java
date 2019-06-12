package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizz.model.Questions;
import com.example.quizz.model.QuizzSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayActivity extends AppCompatActivity {
    private TextView subjectName;
    private TextView subjectId;
    private TextView numOfQuestions;
    private TextView timeToQuizz;
    private Button btnPlay;
    ProgressDialog pd;
    private List<Questions> lstQuestion= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        subjectName = (TextView)findViewById(R.id.txt_subject_name);
        subjectId = (TextView)findViewById(R.id.txt_subject_id);
        numOfQuestions = (TextView)findViewById(R.id.txt_num_questions);
        timeToQuizz = (TextView)findViewById(R.id.txt_time_quizz);
        btnPlay = (Button)findViewById(R.id.btn_play);
        QuizzSession.SCORE = 0;
        pd = new ProgressDialog(PlayActivity.this);
        pd.setMessage("Đang tải dữ liệu...");
        pd.show();
        readQuestions();
        //setUpQuizz();
    }
    private void setUpQuizz(){
        //set info
        //QuizzSession.LSTQUESTIONS.addAll(lstQuestion);
        subjectName.setText(new StringBuilder().append("Tên môn học: ").append(QuizzSession.SUBJECT_NAME).toString());
        subjectId.setText(new StringBuilder().append("Mã môn học: ").append(QuizzSession.SUBJECT_ID.toUpperCase()).toString());
        timeToQuizz.setText(new StringBuilder().append("Thời gian làm bài: ")
                .append((int)QuizzSession.QUIZZ_TIME/60).append(" phút ")
                .append(QuizzSession.QUIZZ_TIME%60)
                .append(" giây").toString());
        //Log.d("Question1:", lstQuestion.get(1).getSubjectId());
        numOfQuestions.setText(String.format("Số lượng câu hỏi: %d", QuizzSession.NUM_OF_QUESTIONS));
        if(QuizzSession.QUIZZ_TIME == QuizzSession.DONT_SET_TIME){
            timeToQuizz.setText(new StringBuilder().append("Thời gian làm bài: ")
                    .append(" VÔ HẠN").toString());
        }
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this, QuizzActivity.class);
                if(QuizzSession.QUIZZ_CATEGORY.equals(QuizzSession.QUIZZ)){
                    Collections.shuffle(lstQuestion);
                }else if(QuizzSession.QUIZZ_CATEGORY.equals(QuizzSession.TEST)){
                    List<Questions> newLstQuestion = ExtendFunc.setupTestcase(lstQuestion);
                    lstQuestion.clear();
                    lstQuestion.addAll(newLstQuestion);
                }
                intent.putExtra("questions", (Serializable) lstQuestion);
                startActivity(intent);
            }
        });

    }
    private void readQuestions(){
        //Get questions
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("questions").child(QuizzSession.SUBJECT_ID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstQuestion.clear();
                pd.dismiss();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Questions question = snapshot.getValue(Questions.class);
                    lstQuestion.add(question);
                }
                setUpQuizz();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
