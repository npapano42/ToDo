package com.todo.nicholaspapano.todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormat
{
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

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

    public static String formatReadableDateTime(ToDoEvent event)
    {
        return formatReadableDate(event) + " " + formatReadableTime(event);
    }

    public static String formatReadableDate(ToDoEvent event)
    {
        int month = event.getRemindTime().getMonthValue();
        int dayOfMonth = event.getRemindTime().getDayOfMonth();
        int year = event.getRemindTime().getYear();
        return String.format(Locale.US, "%02d", month) + "/" +
                String.format(Locale.US, "%02d", dayOfMonth) + "/" +
                String.format(Locale.US, "%04d", year);

    }

    public static String formatReadableTime(ToDoEvent event)
    {
        String amPM = "AM";
        int hour = event.getRemindTime().getHour();
        if (hour >= 12)
        {
            amPM = "PM";
            if (hour > 12)
                hour -= 12;
        }
        return String.format(Locale.US, "%02d", hour) + ":" +
                String.format(Locale.US, "%02d", event.getRemindTime().getMinute()) + " " + amPM;
    }
}
