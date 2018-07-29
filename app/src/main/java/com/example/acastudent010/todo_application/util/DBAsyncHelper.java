package com.example.acastudent010.todo_application.util;


import android.os.AsyncTask;

import com.example.acastudent010.todo_application.model.ToDoItem;
import com.example.acastudent010.todo_application.room.ToDoDataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBAsyncHelper {
    public static List<ToDoItem> retrieveData(final ToDoDataBase toDoDataBase){
        GetDataAsync task = new GetDataAsync(toDoDataBase);
        task.execute();
        List<ToDoItem> toDoItems = new ArrayList<>();
        try {
            if (task == null)
                return new ArrayList<>();
            toDoItems = task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toDoItems;
    }

    public static void insertData(final ToDoDataBase toDoDataBase, ToDoItem[] toDoItems){
        AddDataAsync task = new AddDataAsync(toDoDataBase, toDoItems);
        task.execute();
    }

    public static void updateData(final ToDoDataBase toDoDataBase, ToDoItem item){
        UpdateDataAsync task = new UpdateDataAsync(toDoDataBase, item);
        task.execute();
    }

    public static void deleteData(final ToDoDataBase toDoDataBase, ToDoItem item){
        DeleteDataAsync task = new DeleteDataAsync(toDoDataBase, item);
        task.execute();
    }






    private static class GetDataAsync extends AsyncTask<Void ,Void ,List<ToDoItem>>{
        private final ToDoDataBase mDB;

        private GetDataAsync(ToDoDataBase mDB) {
            this.mDB = mDB;
        }

        @Override
        protected List<ToDoItem> doInBackground(Void... voids) {
            return mDB.todoDao().getAll();
        }
    }


    private static class AddDataAsync extends AsyncTask<Void ,Void ,Void>{
        private final ToDoDataBase mDB;
        private ToDoItem[] toDoItems;

        private AddDataAsync(ToDoDataBase mDB, ToDoItem ...toDoItems) {
            this.mDB = mDB;
            this.toDoItems = Arrays.copyOf(toDoItems, toDoItems.length);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDB.todoDao().insertAll(toDoItems);
            return null;
        }
    }


    private static class UpdateDataAsync extends AsyncTask<Void ,Void ,Void>{
        private final ToDoDataBase mDB;
        private ToDoItem toDoItem;

        private UpdateDataAsync(ToDoDataBase mDB, ToDoItem item) {
            this.mDB = mDB;
            toDoItem = item;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDB.todoDao().update(toDoItem);
            return null;
        }
    }


    private static class DeleteDataAsync extends AsyncTask<Void ,Void ,Void>{
        private final ToDoDataBase mDB;
        private ToDoItem toDoItem;

        private DeleteDataAsync(ToDoDataBase mDB, ToDoItem item) {
            this.mDB = mDB;
            toDoItem = item;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDB.todoDao().delete(toDoItem);
            return null;
        }
    }
}
