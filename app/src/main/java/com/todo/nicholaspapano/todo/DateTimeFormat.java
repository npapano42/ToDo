package com.todo.nicholaspapano.todo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormat
{
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a");
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm a");

    public static String formatDateTime(String dateTime)
    {
        return LocalDateTime.parse(dateTime).format(dateTimeFormat);
    }


    public static String formatDate(String date)
    {
        return LocalDateTime.parse(date).format(dateFormat);
    }

    public static String formatTime(String time)
    {
        return LocalDateTime.parse(time).format(timeFormat);
    }
}
