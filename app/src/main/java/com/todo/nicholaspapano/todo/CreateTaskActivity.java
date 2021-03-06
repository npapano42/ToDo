package com.todo.nicholaspapano.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity
{
    Toolbar tbCreateTask;
    EditText etTaskName, etTaskDate, etTaskTime;
    Button bDone, bTaskDate, bTaskTime;

    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;

    String selDate, selTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        tbCreateTask = findViewById(R.id.tbCreateTask);
        etTaskName = findViewById(R.id.etTaskName);
        etTaskDate = findViewById(R.id.etTaskDate);
        etTaskTime = findViewById(R.id.etTaskTime);
        bDone = findViewById(R.id.bDone);
        bTaskDate = findViewById(R.id.bTaskDate);
        bTaskTime = findViewById(R.id.bTaskTime);

        tbCreateTask.setTitle("Create a task");
        setSupportActionBar(tbCreateTask);

        bTaskDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LocalDateTime date = LocalDateTime.now();
                DatePickerDialog datePicker = new DatePickerDialog(
                        CreateTaskActivity.this,
                        dateSetListener,
                        date.getYear(),
                        date.getMonthValue() -1,
                        date.getDayOfMonth()
                );

                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePicker.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                selDate = String.format(Locale.US, "%02d", ++month) + "/" +
                        String.format(Locale.US, "%02d", dayOfMonth) + "/" +
                        String.format(Locale.US, "%04d", year);

                etTaskDate.setText(selDate);
            }
        };

        bTaskTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LocalDateTime time = LocalDateTime.now();
                TimePickerDialog timePicker = new TimePickerDialog(
                        CreateTaskActivity.this,
                        timeSetListener,
                        time.getHour(),
                        time.getMinute(),
                        false
                );


                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                timePicker.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                selTime = String.format(Locale.US, "%02d", hourOfDay) + ":" +
                        String.format(Locale.US, "%02d", minute);
                String amPM = "AM";
                if (hourOfDay >= 12)
                {
                    amPM = "PM";
                    if (hourOfDay > 12)
                        hourOfDay -= 12;
                }
                String time = String.format(Locale.US, "%02d", hourOfDay) + ":" +
                        String.format(Locale.US, "%02d", minute) + " " + amPM;

                etTaskTime.setText(time);
            }
        };


        bDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String dateTime = selDate + " " + selTime;

                if (etTaskName.getText().length() == 0)
                {
                    Toast.makeText(CreateTaskActivity.this, "No task name specified!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etTaskDate.getText().length() == 0)
                {
                    Toast.makeText(CreateTaskActivity.this, "No task date specified!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etTaskTime.getText().length() == 0)
                {
                    Toast.makeText(CreateTaskActivity.this, "No task time specified!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (LocalDateTime.parse(dateTime, DateTimeFormat.dateTimeFormat).isBefore(LocalDateTime.now()))
                {
                    Toast.makeText(CreateTaskActivity.this, "Reminder can't be in the past!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ToDoEvent event = new ToDoEvent(etTaskName.getText().toString(), LocalDateTime.parse(dateTime, DateTimeFormat.dateTimeFormat), 0);
                ToDoDB db = new ToDoDB(CreateTaskActivity.this);
                if (db.insert(event))
                {
                    createJob(event);
                    Toast.makeText(CreateTaskActivity.this, "Reminder added successfully", Toast.LENGTH_SHORT).show();
                    Intent intentHome = new Intent(CreateTaskActivity.this, HomeActivity.class);
                    startActivity(intentHome);
                }
                else
                    Toast.makeText(CreateTaskActivity.this, "Error while saving reminder", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createJob(ToDoEvent event)
    {
        JobInfo job = new JobInfo.Builder(event.getEventID(), new ComponentName(this, EventNotificationJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        jobScheduler.schedule(job);
//        Toast.makeText(CreateTaskActivity.this, "Job added :P", Toast.LENGTH_SHORT).show();

    }
}
