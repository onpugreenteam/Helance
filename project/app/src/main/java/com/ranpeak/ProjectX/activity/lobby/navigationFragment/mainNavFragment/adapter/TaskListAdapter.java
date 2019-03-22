package com.ranpeak.ProjectX.activity.lobby.navigationFragment.mainNavFragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.TaskActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.dto.TaskDTO;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<TaskDTO> data;
    private ArrayList<String> images = new ArrayList<>();
    private Context context;

    public TaskListAdapter(List<TaskDTO> data, ArrayList<String> images, Context context) {
        this.data = data;
        this.images = images;
        this.context = context;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    private static String getRandomChestItem(ArrayList<String> images) {
        return images.get(new Random().nextInt(images.size()));
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskDTO item = data.get(position);
        holder.headline.setText(item.getHeadLine());
        holder.subject.setText(item.getSubject());
        holder.date.setText(item.getDateStart());
        holder.price.setText(String.valueOf(item.getPrice()));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        URL url = null;
        try {
            url = new URL(getRandomChestItem(images));
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.profile_user.setImageBitmap(bmp);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

//        Glide.with(context)
//                .asBitmap()
//                .load(images.get(position))
//                .into(holder.profile_user);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<TaskDTO> data) {
        this.data = data;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements Activity {
        CardView cardView;
        TextView headline;
        TextView date;
        TextView subject;
        ImageView profile_user;
        TextView price;

        public TaskViewHolder(View itemView) {
            super(itemView);
            findViewById();
            onListener();

        }

        @Override
        public void findViewById() {
            cardView = itemView.findViewById(R.id.cardView);
            headline = itemView.findViewById(R.id.headline);
            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.date);
            profile_user = itemView.findViewById(R.id.profile_user);
            price = itemView.findViewById(R.id.price);
        }

        @Override
        public void onListener() {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Open task", Toast.LENGTH_LONG).show();
                    v.getContext().startActivity(new Intent(v.getContext(), TaskActivity.class));
                }
            });

        }
    }

}
