package com.example.quizz;

import com.example.quizz.model.Questions;
import com.example.quizz.model.QuizzSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExtendFunc {
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
}
