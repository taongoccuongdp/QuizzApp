package com.example.quizz;

import com.example.quizz.model.Questions;
import com.example.quizz.model.QuizzSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class ExtendFunc {
    public static int year = 0;
    public static int month = 0;
    public static int dayOfMonth = 0;
    public static int hour = 0;
    public static int minute = 0;
    public static List<Questions> setupTestcase(List<Questions> originTestcase){
        List<Questions> newTestcase = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i< QuizzSession.NUM_OF_QUESTIONS; i++){
            Collections.shuffle(originTestcase);
            Questions q = originTestcase.get(random.nextInt(originTestcase.size()));
            originTestcase.remove(q);
            newTestcase.add(q);
        }
        return newTestcase;
    }
    public static String DateToString(int year, int month, int dayOfMonth, int hour, int minute){
        return new StringBuilder()
                .append(hour)
                .append(":")
                .append(minute)
                .append(" ")
                .append(dayOfMonth)
                .append("-")
                .append(month)
                .append("-")
                .append(year)
                .toString();
    }
    public static void StringToDate(String date){
            String[] s = date.split(" ");
            String[] time = s[0].split(":");
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
            String[] d = s[1].split("-");
            dayOfMonth = Integer.parseInt(d[0]);
            month = Integer.parseInt(d[1]);
            year = Integer.parseInt(d[2]);
    }

}
