package com.example.android.quizapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class QuizDetails extends AppCompatActivity {

    MyDBHelper myDBHelper;
    public static final String TAG= "ye raha";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_details);
        myDBHelper = new MyDBHelper(this);
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
      String str =  myDBHelper.getTableAsString(db, DBContract.DBEntry.TABLE_NAME);
        Log.d(TAG, "onCreate: " + str);
    }
}
