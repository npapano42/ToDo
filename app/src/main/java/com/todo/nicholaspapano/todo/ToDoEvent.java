package com.todo.nicholaspapano.todo;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToDoEvent implements Serializable
{
    private String name;
    private LocalDateTime remindTime;
    private int eventID;

    public ToDoEvent(String name, LocalDateTime remindTime, int eventID)
    {
        this.name = name;
        this.remindTime = remindTime;
        this.eventID = eventID;
    }

    public String getName()
    {
        return name;
    }

    public LocalDateTime getRemindTime()
    {
        return remindTime;
    }

    public int getEventID()
    {
        return eventID;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public void setRemindTime(LocalDateTime remindTime)
    {
        this.remindTime = remindTime;
    }

    public void setEventID(int eventID)
    {
        this.eventID = eventID;
    }

    public static ToDoEvent getEventByID(int eventID, Context context)
    {
        ToDoDB db = new ToDoDB(context);
        Cursor cursor = db.selectEventByID(eventID);
        while (cursor.moveToNext())
        {
            LocalDateTime dateTime = LocalDateTime.parse(cursor.getString(2), DateTimeFormat.dateTimeFormat);
            return new ToDoEvent(cursor.getString(1), dateTime, cursor.getInt(0));
        }
        return null;
    }

    public static List<ToDoEvent> getAllEvents(Context context)
    {
        ToDoDB db = new ToDoDB(context);
        List<ToDoEvent> events = new ArrayList<>();
        Cursor cursor = db.selectAll();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            ToDoEvent event = new ToDoEvent(name, LocalDateTime.parse(date, DateTimeFormat.dateTimeFormat), id);
            events.add(event);
        }

        return events;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || obj.getClass() == getClass())
            return false;
        ToDoEvent that = (ToDoEvent) obj;
        return name.equals(that.name) && remindTime.equals(that.remindTime) && eventID == that.eventID;
    }
}
