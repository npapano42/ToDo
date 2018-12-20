package com.todo.nicholaspapano.todo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HomeActivity extends AppCompatActivity
{

    RecyclerView rvTasks;
    FloatingActionButton addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addTask = (FloatingActionButton) findViewById(R.id.fabAddTask);
        rvTasks = (RecyclerView) findViewById(R.id.recyclerTasks);

        addTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*
                Intent intentAddTask = new Intent(HomeActivity.this, /TheAddTaskClass.class/);
                startActivity(intentAddTask);
                */
            }
        });
    }
}
