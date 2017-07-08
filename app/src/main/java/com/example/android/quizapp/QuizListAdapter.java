package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.QuizViewHolder> {


    private final Context context;
    private List<Category> list;

    public QuizListAdapter(Context context, List<Category> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quiz_listitem, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        Category category = list.get(position);
        holder.topicName.setText(category.categoryName);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView topicName;

        public QuizViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            topicName = (TextView) itemView.findViewById(R.id.quiz_topic);

        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, QuizDetails.class));
        }
    }
}
