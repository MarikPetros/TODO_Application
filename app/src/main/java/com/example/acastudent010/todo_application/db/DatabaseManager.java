package com.example.acastudent010.todo_application.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.UUID;

import com.example.acastudent010.todo_application.IObserver;
import com.example.acastudent010.todo_application.SomeEvent;
import com.example.acastudent010.todo_application.model.ToDoItem;
import com.example.acastudent010.todo_application.db.TodoItemContract.TodoEntry;


public class DatabaseManager implements IObserver {
    private SQLiteDatabase mDataBase;
    private DbHelper dbHelper;
    private static DatabaseManager sInstance;

    private DatabaseManager(Context context) {
        dbHelper =  new DbHelper(context);
        mDataBase = dbHelper.getWritableDatabase();
    }

    public static DatabaseManager getDataBaseManagerInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context);
        }
        return sInstance;
    }

    private ContentValues getContentValues(ToDoItem item) {
        ContentValues cv = new ContentValues();
        cv.put(TodoEntry.TITLE, item.getTitle());
        cv.put(TodoEntry.DESCRIPTION, item.getDescription());
        cv.put(TodoEntry.DATE, item.getDate().toString());
        cv.put(TodoEntry.CHECK_REMIND, item.isCheckRemind());
        cv.put(TodoEntry.IS_REPEAT, item.isRepeat());
        cv.put(TodoEntry.REPEAT_TYPE, String.valueOf(item.getRepeatType()));
        cv.put(TodoEntry.PRIORITY, item.getPriority());
        return cv;
    }

    private TodoCursorWrapper query(String selection, String[] selectionArgs, String orderBy) {
        Cursor cursor = mDataBase
                .query(TodoEntry.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        orderBy);
        return new TodoCursorWrapper(cursor);
    }


    // TODO pass todo item as parameter
    public void insertTodoItem(ToDoItem item) {
        long insertedId = mDataBase.insert(TodoEntry.TABLE_NAME, null, getContentValues(item));

        Log.v(DatabaseManager.class.getSimpleName(), "insertedId: " + insertedId);
    }

    public ToDoItem getTodoItemById(int id) {
        ToDoItem toDoItem = new ToDoItem();
        String selection = TodoEntry._ID + "= ? ";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        TodoCursorWrapper cursor = query(selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            toDoItem = cursor.getToDoItem();
            cursor.close();
        }
        return toDoItem;
    }

    public List<ToDoItem> getAllTodoItems() {
        List<ToDoItem> toDoItems = new ArrayList<>();
       try (TodoCursorWrapper cursor = query(null, null, null);) {
           while (cursor.moveToNext()) {
               toDoItems.add(cursor.getToDoItem());
           }
       }
        return toDoItems;
    }


    public void updateToDoItem(ToDoItem item) {
        ContentValues cv = getContentValues(item);
        long replacedId = mDataBase.replace(TodoEntry.TABLE_NAME, null, cv);
    }

    public void deleteItem(long id) {
        String selection = TodoEntry._ID + "= ? ";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        mDataBase.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void removeSelectedRows() {

    }

    public int getRowCount() {
        int count;
        try (TodoCursorWrapper cursor = query(null, null, null)) {
            count = cursor.getCount();
        }
        return count;
    }

    public void sortDB() {
        String orderbyDate = TodoEntry.DATE;
        try (TodoCursorWrapper cursor = query(null, null, orderbyDate)) {
            dbHelper.onUpgrade(mDataBase,1,2);
        }
    }

    @Override
    public void onStateChanged(

    ) {

    }

    private class TodoCursorWrapper extends CursorWrapper {
        public TodoCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        private ToDoItem getToDoItem() {
            ToDoItem toDoItem = new ToDoItem();
            String title = getString(getColumnIndex(TodoEntry.TITLE));
            String description = getString(getColumnIndex(TodoEntry.DESCRIPTION));
            long date = getLong(getColumnIndex(TodoEntry.DATE));
            String checkRemind = getString(getColumnIndex(TodoEntry.CHECK_REMIND));
            String isRepeat = getString(getColumnIndex(TodoEntry.IS_REPEAT));
            String repeatType = getString(getColumnIndex(TodoEntry.REPEAT_TYPE));
            String priority = getString(getColumnIndex(TodoEntry.PRIORITY));
            // TODO assign above values to TodoItem instance
            toDoItem.setTitle(title);
            toDoItem.setDescription(description);
            toDoItem.setDate(new Date(date));
            toDoItem.setCheckRemind(Boolean.valueOf(checkRemind));
            toDoItem.setRepeat(Boolean.valueOf(isRepeat));
            toDoItem.setRepeatType(ToDoItem.Repeat.valueOf(repeatType));
            toDoItem.setPriority(Integer.valueOf(priority));
            return toDoItem;
        }
    }
}
