package com.example.acastudent010.todo_application.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.acastudent010.todo_application.db.TodoItemContract.TodoEntry;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TODO_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_TODO_ITEMS);
    }

    private static final String SQL_CREATE_TODO_ITEMS =
            "CREATE TABLE " + TodoEntry.TABLE_NAME + " (" +
                    TodoEntry._ID + " INTEGER PRIMARY KEY," +
                    TodoEntry.TITLE + " TEXT NOT NULL," +
                    TodoEntry.DESCRIPTION + " TEXT," +
                    TodoEntry.DATE + " DATE," +
                    TodoEntry.CHECK_REMIND + " BOOLEAN," +
                    TodoEntry.IS_REPEAT + " BOOLEAN," +
                    TodoEntry.REPEAT_TYPE+"TEXT," +
                    TodoEntry.PRIORITY + "INTEGER)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;
}
