package com.example.android.quizapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.android.quizapp.R;
import com.example.android.quizapp.adapters.QuizListAdapter;
import com.example.android.quizapp.database.DBContract;
import com.example.android.quizapp.database.DBHelper;
import com.example.android.quizapp.models.Category;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
	implements NavigationView.OnNavigationItemSelectedListener {

	DBHelper DBHelper;
	List<Category> categoryList = new ArrayList<>();

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		DBHelper = new DBHelper(this);

		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + DBContract.DBEntry.TABLE_NAME + ";", null);

		for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
			Category category = new Category(res.getInt(res.getColumnIndex("_id")),
				res.getString(res.getColumnIndex("Topic")));
			categoryList.add(category);
		}

		res.close();

		RecyclerView quizList = (RecyclerView) findViewById(R.id.quiz_list);
		quizList.setHasFixedSize(true);
		QuizListAdapter quizListAdapter = new QuizListAdapter(this, categoryList, db);
		quizList.setLayoutManager(new GridLayoutManager(this, 2));
		quizList.setAdapter(quizListAdapter);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(
			view -> Snackbar.make(view, "To add Quiz. In Progress", Snackbar.LENGTH_LONG)
				.setAction("Action", null)
				.show());

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle =
			new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		View navHeaderView = navigationView.getHeaderView(0);
		TextView nameField = (TextView) navHeaderView.findViewById(R.id.nav_text1);
		TextView emailField = (TextView) navHeaderView.findViewById(R.id.nav_text2);
		nameField.setText(getIntent().getStringExtra("username"));
		emailField.setText(getIntent().getStringExtra("useremail"));
	}

	@Override public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody") @Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.check_users) {
			startActivity(new Intent(MainActivity.this, CheckUsersActivity.class));
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
