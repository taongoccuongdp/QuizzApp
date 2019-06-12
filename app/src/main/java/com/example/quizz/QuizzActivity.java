package com.example.quizz;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizz.model.Questions;
import com.example.quizz.model.QuizzSession;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.waveloadingview.WaveLoadingView;

public class QuizzActivity extends AppCompatActivity {
    private List<Questions> lstQuestions;
    private WaveLoadingView timeCountdown;
    private TextView question;
    private TextView option1;
    private TextView option2;
    private TextView option3;
    private TextView option4;
    private ImageView imgAnswer;
    private TextView textAnswer;
    private TextView questionCounter;
    private String rightOpt;
    private int timeToQuizz;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        Intent playIntent = getIntent();
        lstQuestions = (ArrayList<Questions>) playIntent.getSerializableExtra("questions");
        QuizzSession.NUM_OF_QUESTIONS = lstQuestions.size();
        //Log.d("subject id", lstQuestions.get(1).getSubjectId());
        initView();
        timeToQuizz = 0;
        //Show first question
        showQuestion();
        //set event
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("option1");
                option1.setBackgroundColor(Color.BLUE);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("option2");
                option2.setBackgroundColor(Color.BLUE);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("option3");
                option3.setBackgroundColor(Color.BLUE);
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("option4");
                option4.setBackgroundColor(Color.BLUE);
            }
        });
    }

    private void checkAnswer(String chooseOpt) {
        timerPause();
        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);
        option4.setClickable(false);
        if (rightOpt.equals(chooseOpt)) {
            QuizzSession.SCORE++;
            imgAnswer.setImageResource(R.drawable.correct_answer);
            textAnswer.setText("Chuẩn rồi!!");
            Handler handlerTrue = new Handler();
            imgAnswer.setVisibility(View.VISIBLE);
            textAnswer.setVisibility(View.VISIBLE);
            handlerTrue.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    showQuestion();
                    restoreStatus();
                }
            }, 2000);
        } else {
            imgAnswer.setImageResource(R.drawable.wrong_answer);
            textAnswer.setText("Sai rồi!!");

            imgAnswer.setVisibility(View.VISIBLE);
            textAnswer.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    showQuestion();
                    restoreStatus();
                }
            }, 2000);
        }
    }

    private void qCounter() {
        questionCounter.setText(new StringBuilder().append("Câu hỏi: ").append(QuizzSession.CURRENT_QUESTION_ID + 1).append(" / ").append(QuizzSession.NUM_OF_QUESTIONS));
    }

    private void initView() {
        timeCountdown = (WaveLoadingView) findViewById(R.id.pb_timeCountDown);
        question = (TextView) findViewById(R.id.txt_question);
        option1 = (TextView) findViewById(R.id.txt_option1);
        option2 = (TextView) findViewById(R.id.txt_option2);
        option3 = (TextView) findViewById(R.id.txt_option3);
        option4 = (TextView) findViewById(R.id.txt_option4);
        //txtCountdown = (TextView)findViewById(R.id.txt_timeCountDown);
        questionCounter = (TextView) findViewById(R.id.txt_questions);
        textAnswer = (TextView) findViewById(R.id.txt_answer);
        imgAnswer = (ImageView) findViewById(R.id.img_answer);
        imgAnswer.setVisibility(View.INVISIBLE);
        textAnswer.setVisibility(View.INVISIBLE);
        if (QuizzSession.QUIZZ_CATEGORY == QuizzSession.QUIZZ) {
            timeCountdown.setVisibility(View.INVISIBLE);
        } else {
            //Neu la test thi se hien thi cai nay len
        }

    }

    private void showQuestion() {
        if (QuizzSession.CURRENT_QUESTION_ID < lstQuestions.size()) {
            qCounter();
            //Show question
            question.setText(new StringBuilder().append("Câu ").append(QuizzSession.CURRENT_QUESTION_ID + 1).append(": ").append(lstQuestions.get(QuizzSession.CURRENT_QUESTION_ID).getQuestion()));
            option1.setText(lstQuestions.get(QuizzSession.CURRENT_QUESTION_ID).getOption1());
            option2.setText(lstQuestions.get(QuizzSession.CURRENT_QUESTION_ID).getOption2());
            option3.setText(lstQuestions.get(QuizzSession.CURRENT_QUESTION_ID).getOption3());
            option4.setText(lstQuestions.get(QuizzSession.CURRENT_QUESTION_ID).getOption4());
            rightOpt = lstQuestions.get(QuizzSession.CURRENT_QUESTION_ID).getRightOpt();
            option1.setClickable(true);
            option2.setClickable(true);
            option3.setClickable(true);
            option4.setClickable(true);
            QuizzSession.CURRENT_QUESTION_ID++;
            timerResume();
        } else {
            Intent intent = new Intent(QuizzActivity.this, EndActivity.class);
            intent.putExtra("totalquizztime", timeToQuizz);
            startActivity(intent);
        }
    }

    private void restoreStatus() {
        imgAnswer.setVisibility(View.INVISIBLE);
        textAnswer.setVisibility(View.INVISIBLE);
        option1.setBackgroundColor(Color.WHITE);
        option2.setBackgroundColor(Color.WHITE);
        ;
        option3.setBackgroundColor(Color.WHITE);
        ;
        option4.setBackgroundColor(Color.WHITE);
        ;
    }

    private void timerStart(int timeToLeft) {
        if (QuizzSession.QUIZZ_CATEGORY.equals(QuizzSession.TEST)) {
            timeCountdown.setVisibility(View.VISIBLE);
            timeCountdown.setProgressValue(0);
        } else if (QuizzSession.QUIZZ_CATEGORY.equals(QuizzSession.QUIZZ)) {
            timeCountdown.setVisibility(View.INVISIBLE);
        }
        countDownTimer = new CountDownTimer(timeToLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeCountdown.setProgressValue(timeToQuizz*100/QuizzSession.QUIZZ_TIME);
                timeCountdown.setCenterTitle(String.valueOf(QuizzSession.QUIZZ_TIME - timeToQuizz));
                timeToQuizz++;
                //Log.d("timer", String.valueOf(timeToQuizz));
            }

            @Override
            public void onFinish() {
                timeCountdown.setProgressValue(QuizzSession.QUIZZ_TIME);
                Intent intent = new Intent(QuizzActivity.this, EndActivity.class);
                intent.putExtra("totalquizztime", timeToQuizz);
                startActivity(intent);
            }
        }.start();
    }
    private void timerPause(){
        countDownTimer.cancel();
    }
    private void timerResume(){
        if(QuizzSession.QUIZZ_TIME - timeToQuizz > 0){
            timerStart(QuizzSession.QUIZZ_TIME - timeToQuizz);
            Log.d("time ", String.valueOf(QuizzSession.QUIZZ_TIME - timeToQuizz));
        }else{
            Intent intent = new Intent(QuizzActivity.this, EndActivity.class);
            intent.putExtra("totalquizztime", timeToQuizz);
            startActivity(intent);
        }
    }
}
