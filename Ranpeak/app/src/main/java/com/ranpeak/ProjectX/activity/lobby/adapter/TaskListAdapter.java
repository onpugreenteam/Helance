package com.ranpeak.ProjectX.activity.lobby.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.dto.TaskDTO;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>{

    private List<TaskDTO> data;

    public TaskListAdapter(List<TaskDTO> data) {
        this.data = data;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskDTO item = data.get(position);
        holder.headline.setText(item.getHeadLine());
        holder.subject.setText(item.getSubject());
        holder.date.setText(item.getDateStart());
        holder.price.setText(String.valueOf(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<TaskDTO> data) {
        this.data = data;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView headline;
        TextView date;
        TextView subject;
        ImageView profile_user;
        TextView price;

        public TaskViewHolder(View itemView) {
            super(itemView);
            findViewById();

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(),"ssadas",Toast.LENGTH_LONG).show();
//                }
//            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"ssadas", Toast.LENGTH_LONG).show();
                }
            });
        }

        private void findViewById(){
            cardView = itemView.findViewById(R.id.cardView);
            headline = itemView.findViewById(R.id.headline);
            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.date);
            profile_user = itemView.findViewById(R.id.profile_user);
            price = itemView.findViewById(R.id.price);
        }
    }
}
