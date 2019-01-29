package com.todo.nicholaspapano.todo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class HomeActivity extends AppCompatActivity
{
    // NOTE: DO NOT CHANGE OR ELSE NOTIFICATIONS BREAK
    static final String CHANNEL_ID = "ToDo42";

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

        tbHome.setTitle("Reminder List");
        setSupportActionBar(tbHome);

        createNotificationChannel();

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

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                HomeActivity.this);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to close the app?");

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void setRecyclerViewAdapter(List<ToDoEvent> events)
    {
        rvLayout = new LinearLayoutManager(this);
        rvTasks.setLayoutManager(rvLayout);
        rvAdapter = new RVAdapter(HomeActivity.this, events);
        rvTasks.setAdapter(rvAdapter);

    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            //NOTE: Register the channel with the system; you can't change the importance after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
