package com.example.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton ibAdd;
    ArrayList<Todo> todos;
    RecyclerView rvTodos;
    TodoAdapter todoAdapter;
    EditText etTodoTitle, etTodoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibAdd = findViewById(R.id.ibAdd);
        rvTodos = findViewById(R.id.rvTodos);

        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View todoFormView = inflater.inflate(R.layout.todo_form, null, false);

                etTodoTitle = todoFormView.findViewById(R.id.etTodoTitle);
                etTodoDescription = todoFormView.findViewById(R.id.etTodoDescription);

                Log.d("AddButtonPress", "Add button was pressed");

                new AlertDialog.Builder(MainActivity.this)
                        .setView(todoFormView)
                        .setTitle("Add Todo")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title = etTodoTitle.getText().toString();
                                String description = etTodoDescription.getText().toString();

                                Todo todo = new Todo(title, description);
                                boolean isCreated = new TodoHandler(MainActivity.this).createTodo(todo);
                                
                                if (isCreated) {
                                    Toast.makeText(MainActivity.this, "Todo saved successfully", Toast.LENGTH_SHORT).show();
                                    getTodos();
                                } else {
                                    Toast.makeText(MainActivity.this, "Unable to save todo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
        });
        rvTodos.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getTodos();
    }

    public ArrayList<Todo> readTodos() {
        ArrayList<Todo> todos = new TodoHandler(this).readTodos();
        return todos;
    }

    public void getTodos() {
        todos = readTodos();

        todoAdapter = new TodoAdapter(todos, this);

        rvTodos.setAdapter(todoAdapter);
    }
}