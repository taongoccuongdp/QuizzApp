package com.example.quizz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuestionsHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PtitQuiz";
    private static final String TABLE_NAME = "Questions";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "id";
    private static final String MONHOC_ID = "monhoc_id";
    private static final String QUESTION = "question";
    private static final String ANSWER_A = "answerA";
    private static final String ANSWER_B = "answerB";
    private static final String ANSWER_C = "answerC";
    private static final String ANSWER_D = "answerD";
    private static final String RIGHT_ANSWER = "right_answer";
    public QuestionsHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_questions_table = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + "INTEGER PRIMARY KEY,"+
                MONHOC_ID + "INTEGER,"+
                QUESTION + "TEXT," +
                ANSWER_A + "TEXT," +
                ANSWER_B + "TEXT," +
                ANSWER_C + "TEXT," +
                ANSWER_D + "TEXT," +
                RIGHT_ANSWER + "TEXT)";
        db.execSQL(create_questions_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_questions_table = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(drop_questions_table);
        onCreate(db);
    }
    public void addQuestion(Questions question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MONHOC_ID, question.getMonhocId());
        values.put(QUESTION, question.getQuestion());
        values.put(ANSWER_A, question.getAnswerA());
        values.put(ANSWER_B, question.getAnswerB());
        values.put(ANSWER_C, question.getAnswerC());
        values.put(ANSWER_D, question.getAnswerD());
        values.put(RIGHT_ANSWER, question.getRightAnswer());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Questions getQuestion(int questionId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(questionId)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Questions question = new Questions(cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7));
        return question;
    }
    public List<Questions> getAllQuestion(){
        List<Questions> questions = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Questions question = new Questions(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7));
            questions.add(question);
            cursor.moveToNext();
        }
        return questions;
    }
    public List<Questions> getLimitQuestion(int start, int count){
        List<Questions> questions = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME+" LIMIT " + start + ","+ count;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Questions question = new Questions(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7));
            questions.add(question);
            cursor.moveToNext();
        }
        return questions;
    }
    public void updateQuestion(Questions question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MONHOC_ID, question.getMonhocId());
        values.put(QUESTION, question.getQuestion());
        values.put(ANSWER_A, question.getAnswerA());
        values.put(ANSWER_B, question.getAnswerB());
        values.put(ANSWER_C, question.getAnswerC());
        values.put(ANSWER_D, question.getAnswerD());
        values.put(RIGHT_ANSWER, question.getRightAnswer());
        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(question.getId())});
        db.close();
    }
    public void deleteQuestion(int questionId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(questionId)});
    }
}
