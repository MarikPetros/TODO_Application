package com.example.acastudent010.todo_application;

import com.example.acastudent010.todo_application.model.ToDoItem;


public class SomeEvent {
    private ChangeMode changeMode;
    private ToDoItem toDoItem;

    public SomeEvent(ChangeMode changeMode, ToDoItem toDoItem) {
        this.changeMode = changeMode;
        this.toDoItem = toDoItem;
    }

    public ChangeMode getChangeMode() {
        return changeMode;
    }

    public ToDoItem getToDoItem() {
        return toDoItem;
    }

    public enum ChangeMode{
        ADD, REMOVE, EDIT
    }
}
