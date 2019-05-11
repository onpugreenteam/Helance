package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myResumeFragment.adapter;

import android.app.Activity;
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
import com.ranpeak.ProjectX.dto.ResumeDTO;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyResumeListAdapter extends ListAdapter<ResumeDTO,  MyResumeListAdapter.MyResumeListViewHolder> {

    private Activity activity;
    private OnItemClickListener listener;

    public MyResumeListAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<ResumeDTO> DIFF_CALLBACK = new DiffUtil.ItemCallback<ResumeDTO>() {
        @Override
        public boolean areItemsTheSame(@NonNull ResumeDTO oldItem, @NonNull ResumeDTO newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ResumeDTO oldItem, @NonNull ResumeDTO newItem) {
            return oldItem.getDateStart().equals(newItem.getDateStart())
                    && oldItem.getOpportunities().equals(newItem.getOpportunities())
                    && oldItem.getStatus().equals(newItem.getStatus())
                    && oldItem.getSubject().equals(newItem.getSubject())
                    && oldItem.getUserCountry().equals(newItem.getUserCountry())
                    && oldItem.getUserEmail().equals(newItem.getUserEmail())
                    && oldItem.getUserName().equals(newItem.getUserName())
                    && oldItem.getUserLogin().equals(newItem.getUserLogin());
        }
    };

    @NonNull
    @Override
    public MyResumeListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView =
                LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_resume_profile, viewGroup, false);
        return new MyResumeListViewHolder(itemView, listener, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyResumeListAdapter.MyResumeListViewHolder holder, int position) {
        ResumeDTO currentItem = getItem(position);

        holder.opportunities.setText(currentItem.getOpportunities());
        holder.author.setText(currentItem.getUserName());
        holder.status.setText(currentItem.getStatus());
        holder.subject.setText(currentItem.getSubject());
        holder.views.setText(currentItem.getViews());
        holder.dateStart.setText(currentItem.getDateStart());
//        Glide.with(activity).load(currentItem.getUserAvatar()).into(holder.avatar);
    }

    public ResumeDTO getResumeAt(int position) {
        return getItem(position);
    }

    public class MyResumeListViewHolder extends RecyclerView.ViewHolder implements com.ranpeak.ProjectX.activity.interfaces.Activity {

        private OnItemClickListener listener;
        private Activity activity;
        private TextView status;
        private TextView updateStatus;
        private ImageView settings;
        private CircleImageView avatar;
        private TextView author;
        private TextView subject;
        private TextView dateStart;
        private TextView views;
        private TextView opportunities;

        private MyResumeListViewHolder(@NonNull View itemView, OnItemClickListener listener, Activity activity) {
            super(itemView);
            this.listener = listener;
            this.activity = activity;
            findViewById();
            onListener();
        }

        @Override
        public void findViewById() {
            status = itemView.findViewById(R.id.item_resume_profile_status);
            updateStatus = itemView.findViewById(R.id.item_resume_profile_update_status);
            settings = itemView.findViewById(R.id.item_resume_profile_settings);
            avatar = itemView.findViewById(R.id.item_resume_profile_picrure);
            author = itemView.findViewById(R.id.item_resume_profile_author);
            subject = itemView.findViewById(R.id.item_resume_profile_subject);
            dateStart = itemView.findViewById(R.id.item_resume_profile_date_start);
            views = itemView.findViewById(R.id.item_resume_profile_views_count);
            opportunities = itemView.findViewById(R.id.item_resume_profile_description);

        }

        @Override
        public void onListener() {
            itemView.setOnClickListener(v->{
                if(listener!=null) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
            updateStatus.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onUpdateStatusClick(getItem(position), position);
                    }
                }
            });
            settings.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(activity, settings);
                popupMenu.inflate(R.menu.menu_my_resume);
                popupMenu.setOnMenuItemClickListener(i -> {
                    switch (i.getItemId()) {
                        case R.id.menu_my_resume_edit:
                            if (listener != null) {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    listener.onEditClick(getItem(position));
                                }
                            }
                            break;
                        case R.id.menu_my_resume_delete:
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
        void onItemClick(ResumeDTO resumeDTO);

        void onItemLongClick(ResumeDTO resumeDTO);

        void itemDeletable(boolean resumeDTO);

        void onUpdateStatusClick(ResumeDTO resumeDTO, int pos);

        void onDeleteClick(ResumeDTO resumeDTO);

        void onEditClick(ResumeDTO resumeDTO);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
