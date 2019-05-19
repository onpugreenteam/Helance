package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.forYouNavFragment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company.Helance.R;
import com.company.Helance.activity.viewTaskOrResume.ViewTaskActivity;
import com.company.Helance.interfaces.ILoadMore;
import com.company.Helance.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForYouListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private ILoadMore loadMore;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private List<TaskDTO> data;
    private ArrayList<String> images = new ArrayList<>();
    private Context context;
    private Activity activity;

    public ForYouListAdapter(List<TaskDTO> data, ArrayList<String> images, RecyclerView recyclerView, Activity activity) {
        this.data = data;
        this.images = images;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMore != null) {
                        loadMore.onLoadMore();
                        isLoading = true;
                    }

                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder rv = null;

        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity).inflate(R.layout.task_item, parent, false);
            rv = new TaskViewHolder(view);
        }else if(viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            rv = new LoadingViewHolder(view);
        }

        return rv;
    }

    private static String getRandomChestItem(ArrayList<String> images) {
        return images.get(new Random().nextInt(images.size()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TaskViewHolder){
            TaskDTO item = data.get(position);
            TaskViewHolder viewHolder = (TaskViewHolder) holder;

//            viewHolder.profile_user.setAnimation(AnimationUtils.loadAnimation(activity,R.anim.anim_for_image));
//            viewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(activity,R.anim.anim_card_view));

            viewHolder.author.setText(item.getUserLogin());
            viewHolder.headline.setText(item.getHeadLine());
            viewHolder.subject.setText(item.getSubject());
            viewHolder.date.setText(item.getDateStart());
            viewHolder.price.setText(String.valueOf(item.getPrice()));

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            if(item.getUserAvatar() != null){
                Glide.with(activity)
                        .load(item.getUserAvatar())
                        .into(viewHolder.profile_user);
            }else {
                Glide.with(activity)
                        .load(getRandomChestItem(images))
                        .into(viewHolder.profile_user);
            }

            viewHolder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(),ViewTaskActivity.class);
                intent.putExtra("TaskObject", item);
                v.getContext().startActivity(intent);
            });

        }else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setData(List<TaskDTO> data) {
        this.data = data;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements com.company.Helance.interfaces.Activity {
        CardView cardView;
        TextView author;
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
            author = itemView.findViewById(R.id.task_author);
            headline = itemView.findViewById(R.id.headline);
            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.date);
            profile_user = itemView.findViewById(R.id.profile_user);
            price = itemView.findViewById(R.id.price);
        }

        @Override
        public void onListener() {

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder{

        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar_loading);
        }
    }
}
