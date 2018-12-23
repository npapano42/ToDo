package com.todo.nicholaspapano.todo;

import android.content.Context;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    //TODO: Implement, import cursor for SQLite
//    public ToDoEvent getEventByID(int eventID, Context context)
//    {
//        return null;
//    }

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

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || obj.getClass() == getClass())
            return false;
        ToDoEvent that = (ToDoEvent) obj;
        return name.equals(that.name) && remindTime.equals(that.remindTime) && eventID == that.eventID;
    }
}
