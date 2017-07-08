package com.example.android.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "quizapp2.db";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DBContract.DBEntry.TABLE_NAME + " (" +
        DBContract.DBEntry._ID + " INTEGER PRIMARY KEY," + DBContract.DBEntry.COLUMN_TOPIC_NAME + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + DBContract.DBEntry.TABLE_NAME_2 + " ("+
                DBContract.DBEntry.COLUMN_ID + " INTEGER NOT NULL," + DBContract.DBEntry.COLUMN_QUESTION + " TEXT NOT NULL,"
         + DBContract.DBEntry.COLUMN_OPTIONS + " TEXT NOT NULL," + DBContract.DBEntry.COLUMN_ANSWER + " TEXT NOT NULL);");


        insertData(sqLiteDatabase);
        insertQuestions(sqLiteDatabase);

    }

    private void insertQuestions(SQLiteDatabase sqLiteDatabase) {

        List<String> questions = Arrays.asList("Who is called God of Cricket?",
                "Who is the star of Argentina Football?",
                "Who won 2011 ICC World Cup?",
                "What is Ronaldo Jersey number?",
                "Chess Grandmaster ranked 1 player?",
                "Current Indian Prime Minister?",
                "Current American President?",
                "Current Russian President?",
                "Current Chinese President?",
                "Current German Chancellor?",
                "First Mughal Emperor?",
                "India gained Independence in?",
                "Declaration of Independence was signed in?",
                "Bangladesh got liberation in which year?",
                "What was Sri Lanka called before?");


        List<String> options = Arrays.asList("Dravid, Tendulkar, Laxman, Sehwag",
                "Messi, Ronaldo, Pele, Rooney",
                "Pakistan, Zimbabwe, India, England",
                "11, 7, 78, 14",
                "Carlsen, Anand, Kramnik, Mitsuha",
                "Narendra Modi, Putin, Jinping, Obama",
                "Obama, Trump, Putin, Xi Jinping",
                "Obama, Trump, Putin, Xi Jinping",
                "Obama, Trump, Putin, Xi Jinping",
                "Obama, Merkel, Putin, Xi Jinping",
                "Babur, Humayun, Akbar, Timur",
                "1452, 1947, 1857, 1854",
                "1950, 1776, 1778, 1779",
                "1990, 1776, 1778, 1971",
                "Lonsea, Sealion, Ceylon, Soolin");

        List<String> answers = Arrays.asList("Tendulkar", "Messi", "India", "7", "Carlsen",
                "Narendra Modi", "Trump", "Putin", "Xi Jinping", "Merkel",
                "Babur", "1947", "1776", "1971", "Ceylon");

        ContentValues cv = new ContentValues();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                cv.put(DBContract.DBEntry.COLUMN_ID, i+1);
                cv.put(DBContract.DBEntry.COLUMN_QUESTION, questions.get(5*i + j));
                cv.put(DBContract.DBEntry.COLUMN_OPTIONS, options.get(5*i + j));
                cv.put(DBContract.DBEntry.COLUMN_ANSWER, answers.get(5*i + j));
                sqLiteDatabase.insert(DBContract.DBEntry.TABLE_NAME_2, null, cv);
                cv.clear();
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String getTableAsString(SQLiteDatabase db, String tableName) {
        Log.d("Ye raha", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }


    public void insertData(SQLiteDatabase sqLiteDatabase){
        List<String> list = Arrays.asList("Sports", "Politics", "History");
        ContentValues cv = new ContentValues();
        for(int i = 0; i< list.size(); i++){
            cv.put(DBContract.DBEntry._ID, i+1);
            cv.put(DBContract.DBEntry.COLUMN_TOPIC_NAME, list.get(i));
            sqLiteDatabase.insert(DBContract.DBEntry.TABLE_NAME, null, cv);
            cv.clear();
        }

    }

}
