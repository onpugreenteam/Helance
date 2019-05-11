package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.dto.TaskDTO;


public class MyTaskListAdapter extends ListAdapter<TaskDTO, MyTaskListAdapter.MyTaskListViewHolder> {

    private android.app.Activity activity;
    private OnItemClickListener listener;

    // first param is used to make anim for items
    public MyTaskListAdapter(android.app.Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<TaskDTO> DIFF_CALLBACK = new DiffUtil.ItemCallback<TaskDTO>() {
        @Override
        public boolean areItemsTheSame(@NonNull TaskDTO oldItem, @NonNull TaskDTO newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskDTO oldItem, @NonNull TaskDTO newItem) {
            return oldItem.getHeadLine().equals(newItem.getHeadLine())
                    && oldItem.getSubject().equals(newItem.getSubject())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getDateStart().equals(newItem.getDateStart())
                    && oldItem.getPrice() == newItem.getPrice()
                    && oldItem.getDeadline().equals(newItem.getDeadline())
                    && oldItem.getStatus().equals(newItem.getStatus())
                    && oldItem.getUserLogin().equals(newItem.getUserLogin())
                    && oldItem.getUserEmail().equals(newItem.getUserEmail())
                    && oldItem.getUserName().equals(newItem.getUserName())
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
        TaskDTO currentItem = getItem(position);

        myTaskListViewHolder.price.setText(String.valueOf(currentItem.getPrice()));
        myTaskListViewHolder.subject.setText(currentItem.getSubject());
        myTaskListViewHolder.headline.setText(currentItem.getHeadLine());
        myTaskListViewHolder.deadLine.setText(currentItem.getDeadline());
        myTaskListViewHolder.status.setText(currentItem.getStatus());
        myTaskListViewHolder.author.setText(currentItem.getUserName());
        myTaskListViewHolder.views.setText(String.valueOf(currentItem.getViews()));
        //        Glide.with(activity).load(currentItem.getUserAvatar()).into(myTaskListViewHolder.avatar);
    }

    public TaskDTO getTaskAt(int position) {
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
                popupMenu.inflate(R.menu.menu_my_task);
                popupMenu.setOnMenuItemClickListener(i -> {
                    switch (i.getItemId()) {
                        case R.id.menu_my_task_edit:
                            if (listener != null) {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    listener.onEditClick(getItem(position));
                                }
                            }
                            break;
                        case R.id.menu_my_task_delete:
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
        void onItemClick(TaskDTO task);

        void onItemLongClick(TaskDTO task);

        void itemDeletable(boolean delete);

        void onUpdateStatusClick(TaskDTO taskDTO);

        void onDeleteClick(TaskDTO task);

        void onEditClick(TaskDTO task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
