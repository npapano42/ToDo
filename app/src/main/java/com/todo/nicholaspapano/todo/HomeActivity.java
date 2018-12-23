package com.todo.nicholaspapano.todo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class HomeActivity extends AppCompatActivity
{
    Toolbar tbHome;
    RecyclerView rvTasks;
    FloatingActionButton addTask;

    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tbHome = findViewById(R.id.tbHome);
        addTask = findViewById(R.id.fabAddTask);
        rvTasks = findViewById(R.id.recyclerTasks);

        setSupportActionBar(tbHome);
        getSupportActionBar().setTitle("Reminder List");

        List<ToDoEvent> events = ToDoEvent.getAllEvents(HomeActivity.this);
        setRecyclerViewAdapter(events);

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

    private void setRecyclerViewAdapter(List<ToDoEvent> events)
    {
        rvLayout = new LinearLayoutManager(this);
        rvTasks.setLayoutManager(rvLayout);
        rvAdapter = new RVAdapter(HomeActivity.this, events);
        rvTasks.setAdapter(rvAdapter);

    }
}
