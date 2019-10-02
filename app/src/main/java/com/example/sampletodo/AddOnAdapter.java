package com.example.sampletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddOnAdapter extends RecyclerView.Adapter<AddOnAdapter.ViewHolder> {
    private List<String> todoList;
    private Context context;
    private  onTaskLongClickListener listener;
    private onTaskClickListener clickListener;
    public AddOnAdapter(Context context, List<String> todoList, onTaskLongClickListener listener, onTaskClickListener clickListener) {
        this.todoList = todoList;
        this.context = context;
        this.listener = listener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.taskName.setText( todoList.get(position));
        holder.taskName.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                listener.onTaskLongClicked(position);
                return true;
            }
        });

        holder.taskName.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                 clickListener.onTaskClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taskName;

        public ViewHolder(View view){
            super(view);
            taskName = view.findViewById(android.R.id.text1);
        }
    }

    interface onTaskLongClickListener{
        void onTaskLongClicked(int position);
    }

    interface onTaskClickListener{
        void onTaskClicked(int position);
    }
}
