package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EditTodoActivity extends AppCompatActivity {

    EditText etEditTodoTitle, etEditTodoDescription;
    Button btnSave, btnCancel;
    LinearLayout llButtonsRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        Intent intent = getIntent();

        etEditTodoTitle = findViewById(R.id.etEditTodoTitle);
        etEditTodoDescription = findViewById(R.id.etEditTodoDescription);

        etEditTodoTitle.setText(intent.getStringExtra("title"));
        etEditTodoDescription.setText(intent.getStringExtra("description"));

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        llButtonsRow = findViewById(R.id.llButtonsRow);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = new Todo(etEditTodoTitle.getText().toString(), etEditTodoDescription.getText().toString());
                todo.setId(intent.getIntExtra("id", 1));
                if (new TodoHandler(EditTodoActivity.this).updateTodo(todo)) {
                    Toast.makeText(EditTodoActivity.this, "Todo updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditTodoActivity.this, "Could not update todo", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        btnSave.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        TransitionManager.beginDelayedTransition(llButtonsRow);
        super.onBackPressed();
    }
}