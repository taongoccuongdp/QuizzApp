package com.example.quizz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RankHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PtitQuiz";
    private static final String TABLE_NAME = "Rank";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "id";
    private static final String USER_ID = "userid";
    private static final String MONHOC_ID = "monhocid";
    private static final String SCORE = "score";
    public RankHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_rank_table = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + "INTEGER PRIMARY KEY,"+
                USER_ID + "INTEGER,"+
                MONHOC_ID + "INTEGER," +
                SCORE + "INTEGER)";
        db.execSQL(create_rank_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_rank_table = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(drop_rank_table);
        onCreate(db);
    }
    public void addRank(Rank rank){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, rank.getUserId());
        values.put(MONHOC_ID, rank.getMonhocId());
        values.put(SCORE, rank.getScore());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Rank getRank(int rankId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "id=?", new String[]{String.valueOf(rankId)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Rank rank = new Rank(cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getInt(3));
        return rank;
    }
    public List<Rank> getAllRank(){
        List<Rank> ranks = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            Rank rank = new Rank(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3));
            ranks.add(rank);
            cursor.moveToNext();
        }
        return ranks;
    }
    public void updateRank(Rank rank){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, rank.getUserId());
        values.put(MONHOC_ID, rank.getMonhocId());
        values.put(SCORE, rank.getScore());
        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(rank.getId())});
        db.close();
    }
    public void deleteRank(int rankId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(rankId)});
    }
}
