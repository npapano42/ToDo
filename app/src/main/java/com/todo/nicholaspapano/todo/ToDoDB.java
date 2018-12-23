package com.todo.nicholaspapano.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

//NOTE: Warning on Android Developer website for using SQLite, recommends Room
// if database gets big and slows down, use room: https://developer.android.com/training/data-storage/room/
public class ToDoDB extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "ToDo.db";
    private static final String TABLE_NAME = "Events";
    private static final int VERSION = 1;
    private static final String COL_1 = "eventID";
    private static final String COL_2 = "name";
    private static final String COL_3 = "remindDate";

    private static String[] projection = {
            COL_1,
            COL_2,
            COL_3,
    };

    private static final String SQL_CREATE_ENTRIES = "create table " + TABLE_NAME+ " (" +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT)";

    private static final String SQL_DROP_ENTRIES = "drop table if exists "+ TABLE_NAME;

    public ToDoDB(Context context)
    {
        // ctor using context, name CursorFactory, and version number
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DROP_ENTRIES);
    }

    public boolean insert(ToDoEvent event)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_2, event.getName());
        values.put(COL_3, DateTimeFormat.formatDateTime(event.getRemindTime().toString()));

        return db.insert(TABLE_NAME, null, values) != -1;
    }

    public boolean update(ToDoEvent event, int oldEventID)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, event.getName());
        values.put(COL_3, DateTimeFormat.formatDateTime(event.getRemindTime().toString()));
        String selection = COL_1 + " = ?";
        String[] selectionArgs = {String.valueOf(oldEventID)};
        return db.update(TABLE_NAME, values, selection, selectionArgs) != -1;
    }


    public boolean delete(int eventID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_1 + " = ?";
        String[] selectionArgs = {String.valueOf(eventID)};
        return db.delete(TABLE_NAME, selection, selectionArgs) != -1;
    }

    public Cursor selectEventByID(int eventID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "task_id = ?";
        String[] selectionArgs = {String.valueOf(eventID)};
        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }

    public Cursor selectAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME, projection, null, null, null, null, null);
    }
}
