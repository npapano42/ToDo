package com.todo.nicholaspapano.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>
{
    private Context context;
    private List<ToDoEvent> tasks;

    public RVAdapter(Context context, List<ToDoEvent> events)
    {
        this.context = context;
        this.tasks = events;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_to_do_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        ToDoEvent event = tasks.get(holder.getAdapterPosition());
        holder.txtTaskName.setText(event.getName());
        holder.txtTaskTime.setText(DateTimeFormat.formatDateTime(event.getRemindTime().toString()));
        final int taskId = event.getEventID();

        // when a event is pressed, display info
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                ToDoEvent event = tasks.get(holder.getAdapterPosition());

                alertDialog.setTitle(event.getName())
                        .setCancelable(true)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.cancel();
                            }
                        })
                        .setMessage("Reminder at: " + DateTimeFormat.formatDateTime(event.getRemindTime().toString()));
                alertDialog.show();
            }
        });

//        holder.view.setOnLongClickListener(new View.OnLongClickListener()
//        {
//            @Override
//            public boolean onLongClick(final View view)
//            {
//                PopupMenu popupMenu = new PopupMenu(context, view);
//                popupMenu.getMenuInflater().inflate(R.menu.menu_add_remove, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        if(item.getItemId() == R.id.menuItem_editTask){
//                            Intent intent = new Intent(context, UpdateTaskActivity.class);
//                            intent.putExtra("oldTaskId", taskId);
//                            context.startActivity(intent);
//                            notifyItemChanged(holder.getAdapterPosition());
//                        }else if(item.getItemId() == R.id.menuItem_removeTask){
//                            TaskDB taskDB = new TaskDB(context);
//                            if(taskDB.delete(taskId)){
//                                tasks.remove(holder.getAdapterPosition());
//                                notifyItemRemoved(holder.getAdapterPosition());
//                                Snackbar.make(view, "Task Removed", Snackbar.LENGTH_LONG)
//                                        .setAction("Action", null).show();
//                            }
//                            else{
//                                Snackbar.make(view, "Task not removed", Snackbar.LENGTH_LONG)
//                                        .setAction("Action", null).show();
//                            }
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//                return false;
//            }
//        });
    }


    @Override
    public int getItemCount()
    {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtTaskName;
        private TextView txtTaskTime;
        private View view;


        public ViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;
            txtTaskName = itemView.findViewById(R.id.txtTaskName);
            txtTaskTime = itemView.findViewById(R.id.txtTaskDate);
        }
    }
}
