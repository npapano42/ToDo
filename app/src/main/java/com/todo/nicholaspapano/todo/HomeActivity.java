package com.todo.nicholaspapano.todo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomeActivity extends AppCompatActivity
{
    Toolbar tbHome;
    RecyclerView rvTasks;
    FloatingActionButton addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tbHome = findViewById(R.id.tbHome);
        addTask = findViewById(R.id.fabAddTask);
        rvTasks = findViewById(R.id.recyclerTasks);

        setSupportActionBar(tbHome);

        addTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intentAddTask = new Intent(HomeActivity.this, CreateTaskActivity.class);
                startActivity(intentAddTask);
            }
        });

    }
}
