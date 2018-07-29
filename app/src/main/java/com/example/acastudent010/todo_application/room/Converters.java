package com.example.acastudent010.todo_application.room;

import android.arch.persistence.room.TypeConverter;

import com.example.acastudent010.todo_application.model.ToDoItem;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static ToDoItem.Repeat fromRepeatTypeName(String period) {
        return ToDoItem.Repeat.valueOf(period);
    }

    @TypeConverter
    public static String periodFromRepeatType(ToDoItem.Repeat repeatType) {
        String repeatTypeName = "";

        switch (repeatType) {
            case NONE:
                repeatTypeName = "NONE";
                break;
            case DAILY:
                repeatTypeName = "DAILY";
                break;
            case WEEKLY:
                repeatTypeName = "WEEKLY";
                break;
            case MONTHLY:
                repeatTypeName = "MONTHLY";
                break;
            default:
                break;
        }
        return repeatTypeName;
    }
}
