package com.todo.nicholaspapano.todo;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class CreateTaskActivity extends AppCompatActivity
{
    Toolbar tbCreateTask;
    EditText taskName;
    DatePicker taskDate;
    Button bDone;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        tbCreateTask = findViewById(R.id.tbCreateTask);
        taskName = findViewById(R.id.etTaskName);
        taskDate = findViewById(R.id.dpTaskDate);
        bDone = findViewById(R.id.bDone);

        setSupportActionBar(tbCreateTask);

    }
}
