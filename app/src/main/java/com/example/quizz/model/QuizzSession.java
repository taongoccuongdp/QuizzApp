package com.example.quizz.model;

import java.util.ArrayList;
import java.util.List;

public class QuizzSession {
    public static String SUBJECT_NAME = "";
    public static String SUBJECT_ID = "";
    public static int CURRENT_QUESTION_ID = 0;
    public static List<Questions> LSTQUESTIONS = new ArrayList<>();
    public static int NUM_OF_QUESTIONS = 0;
    public static int SCORE = 0;
    public static int QUIZZ_TIME = 0;
    public static final int DONT_SET_TIME = 7200;
    public static final int TEST_TIME = 600; //Tính theo giây
    public static final int NUM_QUESTIONS_FOR_TEST = 3;
    public static final int ALL_QUESTIONS = 0;
    public static String QUIZZ_CATEGORY = "";
    public static final String QUIZZ = "Quiz";
    public static final String TEST = "Test";
    public static void resetSesstion(){
        QUIZZ_TIME = 0;
        SCORE = 0;
        NUM_OF_QUESTIONS = 0;
        LSTQUESTIONS.clear();
        CURRENT_QUESTION_ID = 0;
        SUBJECT_ID = "";
        SUBJECT_NAME = "";
        QUIZZ_CATEGORY = "";
    }
    public static void setUpTest(){

    }
    public static void setUpQuizz(){
        QUIZZ_TIME = DONT_SET_TIME;
        NUM_OF_QUESTIONS = ALL_QUESTIONS;
    }

}
