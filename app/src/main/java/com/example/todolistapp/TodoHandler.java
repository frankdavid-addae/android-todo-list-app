package com.example.todolistapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TodoHandler extends DatabaseHelper {
    public TodoHandler(Context context) {
        super(context);
    }

    // Database CRUD Operations

    // Create a todo
    public boolean createTodo(Todo todo) {
        ContentValues values = new ContentValues();
        // Values to be inserted into the database
        values.put("title", todo.getTitle());
        values.put("description", todo.getDescription());

        SQLiteDatabase db = this.getWritableDatabase();

        // Insert values object into the database table and check if insertion is successful
        boolean isSuccessful = db.insert("todo", null, values) > 0;
        db.close();
        return isSuccessful;
    }

    // Read all todos from db
    public ArrayList<Todo> readTodos() {
        ArrayList<Todo> todos = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo ORDER BY id ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Reading values from the columns
                @SuppressLint("Range") int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));

                // Using the values to create a new instance of the Todo class and it to the todos array list
                Todo todo = new Todo(title, description);
                todo.setId(id);
                todos.add(todo);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return todos;
    }

    // Read a single todo from db
    public Todo readTodo(int todoId) {
        Todo todo = null;
        String sqlQuery = "SELECT * FROM todo WHERE id =" + todoId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));

            todo = new Todo(title, description);

            todo.setId(id); while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return todo;
    }

    // Update a todo
    public boolean updateTodo(Todo todo) {
        ContentValues values = new ContentValues();
        values.put("title", todo.getTitle());
        values.put("description", todo.getDescription());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isSuccessful = db.update("todo", values, "id='"+todo.getId()+"'",null) > 0;
        db.close();
        return isSuccessful;
    }

    // Delete a todo
    public boolean deleteTodo(int id) {
        boolean isDeleted;
        SQLiteDatabase db = this.getWritableDatabase();
        isDeleted = db.delete("todo", "id='"+id+"'", null) > 0;
        db.close();
        return isDeleted;
    }
}
