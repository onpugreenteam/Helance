package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.dataBase.local.dto.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyTaskListAdapter extends ListAdapter<Task, MyTaskListAdapter.MyTaskListViewHolder> {

    private android.app.Activity activity;
    private OnItemClickListener listener;

    // first param is used to make anim for items
    public MyTaskListAdapter(android.app.Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getHeadLine().equals(newItem.getHeadLine())
                    && oldItem.getSubject().equals(newItem.getSubject())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getDateStart().equals(newItem.getDateStart())
                    && oldItem.getPrice().equals(newItem.getPrice())
                    && oldItem.getDeadline().equals(newItem.getDeadline())
                    && oldItem.getStatus().equals(newItem.getStatus())
                    && oldItem.getUserLogin().equals(newItem.getUserLogin())
                    && oldItem.getUserEmail().equals(newItem.getUserEmail())
                    && oldItem.getUserName().equals(newItem.getUserName())
                    && oldItem.getUserAvatar().equals(newItem.getUserAvatar())
                    && oldItem.getUserCountry().equals(newItem.getUserCountry());
        }
    };

    @NonNull
    @Override
    public MyTaskListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_task_profile, viewGroup, false);
        return new MyTaskListViewHolder(itemView, listener, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskListViewHolder myTaskListViewHolder, int position) {
        Task currentItem = getItem(position);

        myTaskListViewHolder.price.setText(currentItem.getPrice());
        myTaskListViewHolder.subject.setText(currentItem.getSubject());
        myTaskListViewHolder.headline.setText(currentItem.getHeadLine());
        myTaskListViewHolder.deadLine.setText(currentItem.getDeadline());
        myTaskListViewHolder.status.setText(currentItem.getStatus());
        myTaskListViewHolder.author.setText(currentItem.getUserName());
        myTaskListViewHolder.views.setText(String.valueOf(currentItem.getViews()));
    }

    public Task getTaskAt(int position) {
        return getItem(position);
    }

    public class MyTaskListViewHolder extends RecyclerView.ViewHolder implements Activity {

        private TextView status;
        private ImageView avatar;
        private TextView author;
        private TextView deadLine;
        private TextView views;
        private TextView headline;
        private TextView subject;
        private TextView price;
        private TextView updateStatus;
        private ImageView taskSettings;
        private ImageView deleteButton;
        private OnItemClickListener listener;
        private android.app.Activity activity;

        private MyTaskListViewHolder(@NonNull View itemView, OnItemClickListener listener, android.app.Activity activity) {
            super(itemView);
            this.listener = listener;
            this.activity = activity;
            findViewById();
            onListener();
        }

        @Override
        public void findViewById() {
            status = itemView.findViewById(R.id.item_task_profile_status);
            avatar = itemView.findViewById(R.id.item_task_profile_avatar);
            author = itemView.findViewById(R.id.item_task_profile_author);
            deadLine = itemView.findViewById(R.id.item_task_profile_date);
            views = itemView.findViewById(R.id.item_task_profile_views_count);
            headline = itemView.findViewById(R.id.item_task_profile_headline);
            subject = itemView.findViewById(R.id.item_task_profile_subject);
            price = itemView.findViewById(R.id.item_task_profile_price);
            updateStatus = itemView.findViewById(R.id.item_task_profile_update_status);
            taskSettings = itemView.findViewById(R.id.item_task_profile_settings);
            deleteButton = itemView.findViewById(R.id.item_task_profile_delete_img);
        }

        @Override
        public void onListener() {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
            itemView.setOnLongClickListener(v -> {
//                if (listener != null) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        taskSettings.setVisibility(View.INVISIBLE);
//                        deleteButton.setVisibility(View.VISIBLE);
//                        listener.onItemLongClick(position);
//                    }
//                }
                return true;
            });
            deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(getItem(position));
                    }
                }
            });
            updateStatus.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onUpdateStatusClick(getItem(position));
                    }
                }
            });
            taskSettings.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(activity, taskSettings);
                popupMenu.inflate(R.menu.my_task_menu);
                popupMenu.setOnMenuItemClickListener(i -> {
                    switch (i.getItemId()) {
                        case R.id.my_task_menu_edit:
                            if (listener != null) {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    listener.onEditClick(getItem(position));
                                }
                            }
                            break;
                        case R.id.my_task_menu_delete:
                            if (listener != null) {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    listener.onDeleteClick(getItem(position));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    return false;
                });
                popupMenu.show();
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);

        void onItemLongClick(Task task);

        void itemDeletable(boolean delete);

        void onUpdateStatusClick(Task task);

        void onDeleteClick(Task task);

        void onEditClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
