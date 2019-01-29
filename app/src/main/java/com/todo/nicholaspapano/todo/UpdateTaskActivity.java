package com.todo.nicholaspapano.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class UpdateTaskActivity extends AppCompatActivity
{
    Toolbar tbEditTask;
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

        tbEditTask = findViewById(R.id.tbCreateTask);
        etTaskName = findViewById(R.id.etTaskName);
        etTaskDate = findViewById(R.id.etTaskDate);
        etTaskTime = findViewById(R.id.etTaskTime);
        bDone = findViewById(R.id.bDone);
        bTaskDate = findViewById(R.id.bTaskDate);
        bTaskTime = findViewById(R.id.bTaskTime);

        tbEditTask.setTitle("Edit task");
        setSupportActionBar(tbEditTask);


        final int oldTaskId = getIntent().getExtras().getInt("oldTaskId");


        ToDoDB taskDB = new ToDoDB(UpdateTaskActivity.this);
        ToDoEvent oldEvent = ToDoEvent.getEventByID(oldTaskId, UpdateTaskActivity.this);

        etTaskName.setText(oldEvent.getName());
        etTaskDate.setText(DateTimeFormat.formatDate(oldEvent.getRemindTime().toString()));
        etTaskTime.setText(DateTimeFormat.formatTime(oldEvent.getRemindTime().toString()));

        bTaskDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LocalDateTime date = LocalDateTime.now();
                DatePickerDialog datePicker = new DatePickerDialog(
                        UpdateTaskActivity.this,
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
                        UpdateTaskActivity.this,
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
                    Toast.makeText(UpdateTaskActivity.this, "No task name specified!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etTaskDate.getText().length() == 0)
                {
                    Toast.makeText(UpdateTaskActivity.this, "No task date specified!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etTaskTime.getText().length() == 0)
                {
                    Toast.makeText(UpdateTaskActivity.this, "No task time specified!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (LocalDateTime.parse(dateTime, DateTimeFormat.dateTimeFormat).isBefore(LocalDateTime.now()))
                {
                    Toast.makeText(UpdateTaskActivity.this, "Reminder can't be in the past!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ToDoEvent event = new ToDoEvent(etTaskName.getText().toString(), LocalDateTime.parse(dateTime, DateTimeFormat.dateTimeFormat), 0);

                ToDoDB db = new ToDoDB(UpdateTaskActivity.this);
                if (db.update(event, oldTaskId))
                {
                    Toast.makeText(UpdateTaskActivity.this, "Reminder edited successfully", Toast.LENGTH_SHORT).show();
                    Intent intentHome = new Intent(UpdateTaskActivity.this, HomeActivity.class);
                    startActivity(intentHome);
                }
                else
                    Toast.makeText(UpdateTaskActivity.this, "Error while editing reminder", Toast.LENGTH_LONG).show();
            }
        });
    }
}
