package com.example.todolistappva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todos_db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_TODO = "todos";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TODO = "todo_text";
    private static final String COLUMN_DATE = "registration_date";
    private static final String COLUMN_ALARM = "alarm_time";

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TODO + " TEXT, " +
                COLUMN_DATE + " INTEGER, " +
                COLUMN_ALARM + " INTEGER" +
                ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public long insertTodo(TodoItem todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO, todo.getTodoText());
        values.put(COLUMN_DATE, todo.getRegistrationDate());
        values.put(COLUMN_ALARM, todo.getAlarmTime());
        long id = db.insert(TABLE_TODO, null, values);
        db.close();
        return id;
    }

    public List<TodoItem> getAllTodos() {
        List<TodoItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_TODO, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TODO));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                long alarm = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ALARM));
                list.add(new TodoItem(id, text, date, alarm));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void updateTodo(TodoItem todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TODO, todo.getTodoText());
        values.put(COLUMN_DATE, todo.getRegistrationDate());
        values.put(COLUMN_ALARM, todo.getAlarmTime());
        db.update(TABLE_TODO, values, COLUMN_ID + "=?", new String[]{String.valueOf(todo.getId())});
        db.close();
    }

    public void deleteTodo(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TODO, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}