package com.example.acastudent010.todo_application.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.acastudent010.todo_application.model.ToDoItem;

@Database(entities = {ToDoItem.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class ToDoDataBase extends RoomDatabase {
    private static ToDoDataBase INSTANCE;

    public abstract ToDoDao todoDao();

    public static ToDoDataBase getTodoDataBase(Context context){
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), ToDoDataBase.class, "todo-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                          //  .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
