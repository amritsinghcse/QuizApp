package com.example.android.quizapp;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "quizapp.db";
    public static final String TAG = "Ye raha";
    private String DB_PATH;
    private Context mContext;
    public SQLiteDatabase mSQLiteDatabase;


    public DatabaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        mContext = context;
        DB_PATH = mContext.getApplicationContext().getPackageName()+"/databases/";
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println("Database exists");
            Log.d(TAG, "DatabaseHelper: DB exists");
            opendatabase();
        } else {
//            System.out.println("Database doesn't exist");
            Log.d(TAG, "DatabaseHelper: No DB exists");
            createdatabase();
        }

    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        mSQLiteDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

    }



    public synchronized void close() {
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.close();
        }
        super.close();
    }


    public String getTableAsString(SQLiteDatabase db, String tableName) {
        Log.d(TAG, "getTableAsString called");
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


    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }

        return checkdb;
    }
    private void copydatabase() throws IOException {

        //Open your local db as the input stream
        InputStream myinput = mContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream("/data/data/(packagename)/databases/(datbasename).sqlite");

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();

    }
//
//    private void copyDatabase(File dbFile) {
//        InputStream is = null;
//        OutputStream os = null;
//        try {
//            mContext.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
//            is = mContext.getAssets().open(DB_NAME);
//            os = new FileOutputStream(dbFile);
//            byte[] buffer = new byte[1024];
//            while (is.read(buffer) > 0) {
//                os.write(buffer);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (os != null) os.close();
//                if (is != null) is.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
