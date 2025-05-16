package com.example.todolistappsqlite; // 본인의 패키지 이름으로 변경

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_database";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TODO = "todos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_REGISTRATION_DATE = "registration_date";
    public static final String COLUMN_IS_COMPLETED = "is_completed";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_TODO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TASK + " TEXT NOT NULL," +
                    COLUMN_REGISTRATION_DATE + " INTEGER NOT NULL," +
                    COLUMN_IS_COMPLETED + " INTEGER NOT NULL DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_TODO;

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insertTodo(TodoItem todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, todo.getTask());
        values.put(COLUMN_REGISTRATION_DATE, todo.getRegistrationDate().getTime());
        values.put(COLUMN_IS_COMPLETED, todo.isCompleted() ? 1 : 0);
        long newRowId = db.insert(TABLE_TODO, null, values);
        db.close();
        return newRowId;
    }

    public List<TodoItem> getAllTodos() {
        List<TodoItem> todoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_TODO,
                null,
                null,
                null,
                null,
                null,
                COLUMN_REGISTRATION_DATE + " ASC" // 등록 날짜 순으로 정렬
        );
        if (cursor.moveToFirst()) {
            do {
                TodoItem todo = new TodoItem(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK)));
                todo.setRegistrationDate(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_REGISTRATION_DATE))));
                todo.setCompleted(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1);
                todoList.add(todo);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return todoList;
    }

    public void updateTodo(TodoItem todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, todo.getTask());
        values.put(COLUMN_IS_COMPLETED, todo.isCompleted() ? 1 : 0);
        int rowsAffected = db.update(
                TABLE_TODO,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(todo.getId())}
        );
        db.close();
    }

    public void deleteTodo(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_TODO,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();
    }
}