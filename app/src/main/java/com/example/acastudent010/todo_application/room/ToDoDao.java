package com.example.acastudent010.todo_application.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.provider.ContactsContract;

import com.example.acastudent010.todo_application.model.ToDoItem;

import java.util.Date;
import java.util.List;

@Dao
public interface ToDoDao {

        @Query("SELECT * FROM toDoItem")
        List<ToDoItem> getAll();

        @Query("SELECT * FROM toDoItem WHERE todoId IN (:todoIds)")
        List<ToDoItem> loadAllByIds(int[] todoIds);

        @Query("SELECT * FROM toDoItem WHERE _title LIKE :title LIMIT 1")
        ToDoItem findByTitle(String title);

        @Query("SELECT * FROM toDoItem ORDER BY _date")
        List<ToDoItem> sort();

        @Insert
        void insertAll(ToDoItem... toDoItems);

        @Update
        void update(ToDoItem item);

        @Delete
        void delete(ToDoItem item);

}
