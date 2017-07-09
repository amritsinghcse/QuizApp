package com.example.android.quizapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.quizapp.R;
import com.example.android.quizapp.adapters.AllUsersAdapter;
import com.example.android.quizapp.database.DBHelper;

public class CheckUsersActivity extends AppCompatActivity {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    AllUsersAdapter allUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_users);
        dbHelper = new DBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.users_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        allUsersAdapter = new AllUsersAdapter(this, dbHelper.getAllUser());
        recyclerView.setAdapter(allUsersAdapter);

    }
}
