package com.example.todolistapp;

import android.content.Context;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {
    ArrayList<Todo> todos;
    Context context;
    ItemClicked itemClicked;
    ViewGroup parent;

    public TodoAdapter(ArrayList<Todo> todos, Context context, ItemClicked itemClicked) {
        this.todos = todos;
        this.context = context;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_holder, parent, false);
        this.parent = parent;
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        holder.tvTodoTitle.setText(todos.get(position).getTitle());
        holder.tvTodoDescription.setText(todos.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    class TodoHolder extends RecyclerView.ViewHolder {
        TextView tvTodoTitle, tvTodoDescription;
        ImageView ivEdit;

        public TodoHolder(@NonNull View itemView) {
            super(itemView);

            tvTodoTitle = itemView.findViewById(R.id.tvTodoTitle);
            tvTodoDescription = itemView.findViewById(R.id.tvTodoDescription);
            ivEdit = itemView.findViewById(R.id.ivEdit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tvTodoDescription.getMaxLines() == 1) {
                        tvTodoDescription.setMaxLines(Integer.MAX_VALUE);
                    } else {
                        tvTodoDescription.setMaxLines(1);
                    }
                    TransitionManager.beginDelayedTransition(parent);
                }
            });

            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClicked.onClick(itemView, getAdapterPosition());
                }
            });
        }
    }

    interface ItemClicked {
        void onClick(View view, int position);
    }
}
