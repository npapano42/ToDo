package com.todo.nicholaspapano.todo;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static com.todo.nicholaspapano.todo.HomeActivity.CHANNEL_ID;

public class EventNotificationJobService extends JobService
{

    @Override
    public boolean onStartJob(JobParameters params)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String eventName;
        try
        {
            eventName = ToDoEvent.getEventByID(params.getJobId(), EventNotificationJobService.this).getName();
        }
        catch (NullPointerException e)
        {
            eventName = "DEFAULT_NAME";
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "ToDoNotification")
                .setSmallIcon(R.drawable.white_add)
                .setContentTitle("ToDo")
                .setContentText(eventName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendIntent)
                .setChannelId(CHANNEL_ID)
                .setAutoCancel(true);


        //notify user
        NotificationManagerCompat.from(this).notify(params.getJobId(), notification.build());
        return false;
    }

    //note: return true if you’d like the system to reschedule the job, false if drop should be abandoned
    @Override
    public boolean onStopJob(JobParameters params)
    {
        return true;
    }
}
