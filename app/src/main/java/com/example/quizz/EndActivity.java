package com.example.quizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.quizz.model.QuizzSession;

public class EndActivity extends AppCompatActivity {
    private TextView subjectName;
    private TextView subjectId;
    private TextView numOfRightAnswer;
    private TextView txtTime;
    int timeToQuizz;
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
    }
    private void initView(){
        subjectName = (TextView)findViewById(R.id.txt_subject_name);
        subjectId = (TextView)findViewById(R.id.txt_subject_id);
        numOfRightAnswer = (TextView)findViewById(R.id.txt_num_right_answers);
        txtTime = (TextView)findViewById(R.id.txt_time_quizz);
    }
}
