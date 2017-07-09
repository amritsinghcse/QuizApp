package com.example.android.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.quizapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.android.quizapp.database.DBContract.DBEntry.COLUMN_USER_EMAIL;
import static com.example.android.quizapp.database.DBContract.DBEntry.COLUMN_USER_NAME;
import static com.example.android.quizapp.database.DBContract.DBEntry.COLUMN_USER_PASSWORD;
import static com.example.android.quizapp.database.DBContract.DBEntry.TABLE_USER;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quizapp2.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DBContract.DBEntry.TABLE_NAME + " (" +
        DBContract.DBEntry._ID + " INTEGER PRIMARY KEY," + DBContract.DBEntry.COLUMN_TOPIC_NAME + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + DBContract.DBEntry.TABLE_NAME_2 + " ("+
                DBContract.DBEntry.COLUMN_ID + " INTEGER NOT NULL," + DBContract.DBEntry.COLUMN_QUESTION + " TEXT NOT NULL,"
         + DBContract.DBEntry.COLUMN_OPTIONS + " TEXT NOT NULL," + DBContract.DBEntry.COLUMN_ANSWER + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT);");


        insertCategories(sqLiteDatabase);
        insertQuestions(sqLiteDatabase);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //fairly small database, drop and re-create on onUpgrade

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.DBEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.DBEntry.TABLE_NAME_2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(sqLiteDatabase);
    }



    private void insertCategories(SQLiteDatabase sqLiteDatabase){
        List<String> list = Arrays.asList("Sports", "Politics", "History");
        ContentValues cv = new ContentValues();
        for(int i = 0; i< list.size(); i++){
            cv.put(DBContract.DBEntry._ID, i+1);
            cv.put(DBContract.DBEntry.COLUMN_TOPIC_NAME, list.get(i));
            sqLiteDatabase.insert(DBContract.DBEntry.TABLE_NAME, null, cv);
            cv.clear();
        }

    }

    private void insertQuestions(SQLiteDatabase sqLiteDatabase) {


        //Pre-existing data

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

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.name);
        values.put(COLUMN_USER_EMAIL, user.email);
        values.put(COLUMN_USER_PASSWORD, user.password);

        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

}
