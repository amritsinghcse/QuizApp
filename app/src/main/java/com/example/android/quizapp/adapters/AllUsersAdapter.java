package com.example.android.quizapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.quizapp.R;
import com.example.android.quizapp.models.User;
import java.util.List;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.UsersViewHolder> {

	private Context context;
	List<User> users;

	public AllUsersAdapter(Context context, List<User> users) {
		this.context = context;
		this.users = users;
	}

	@Override public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.user_details_layout, parent, false);
		return new UsersViewHolder(view);
	}

	@Override public void onBindViewHolder(UsersViewHolder holder, int position) {
		holder.name.setText(users.get(position).getName());
		holder.email.setText(users.get(position).getEmail());
		holder.password.setText(users.get(position).getPassword());
	}

	@Override public int getItemCount() {
		return users.size();
	}

	class UsersViewHolder extends RecyclerView.ViewHolder {

		TextView name, email, password;

		UsersViewHolder(View itemView) {
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.textViewName);
			email = (TextView) itemView.findViewById(R.id.textViewEmail);
			password = (TextView) itemView.findViewById(R.id.textViewPassword);
		}
	}
}
