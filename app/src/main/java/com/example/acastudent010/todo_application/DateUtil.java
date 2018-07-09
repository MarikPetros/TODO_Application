package com.example.acastudent010.todo_application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class DateUtil {
    public static String formatDateToLongStile(Date date){
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.DEFAULT);
        return  dateFormat.format(date);
    }
}
