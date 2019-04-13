package com.ranpeak.ProjectX.activity.lobby.navigationFragment.resumesNavFragment.adapter;

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
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.ResumeActivity;
import com.ranpeak.ProjectX.activity.TaskActivity;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.tasksNavFragment.ILoadMore;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResumeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private ILoadMore loadMore;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private List<ResumeDTO> data;
    private ArrayList<String> images = new ArrayList<>();
    private Context context;
    private Activity activity;

    public ResumeListAdapter(List<ResumeDTO> data, ArrayList<String> images, RecyclerView recyclerView, Activity activity) {
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
            View view = LayoutInflater.from(activity).inflate(R.layout.resume_item, parent, false);
            rv = new ResumeViewHolder(view);
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

        if(holder instanceof ResumeViewHolder){
            ResumeDTO item = data.get(position);
            ResumeViewHolder viewHolder = (ResumeViewHolder) holder;

            viewHolder.profile_user.setAnimation(AnimationUtils.loadAnimation(activity,R.anim.anim_for_image));
            viewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(activity,R.anim.anim_card_view));

            viewHolder.author.setText(item.getEmployee());
            viewHolder.text.setText(item.getText());
            viewHolder.subject.setText(item.getSubject());
            viewHolder.date.setText(item.getDateStart());

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Glide.with(activity).load(getRandomChestItem(images)).into(viewHolder.profile_user);

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

    public void setData(List<ResumeDTO> data) {
        this.data = data;
    }



    public class ResumeViewHolder extends RecyclerView.ViewHolder implements com.ranpeak.ProjectX.activity.interfaces.Activity {
        CardView cardView;
        TextView author;
        TextView text;
        TextView date;
        TextView subject;
        ImageView profile_user;

        public ResumeViewHolder(View itemView) {
            super(itemView);
            findViewById();
            onListener();

        }

        @Override
        public void findViewById() {
            cardView = itemView.findViewById(R.id.cardView11);
            author = itemView.findViewById(R.id.resume_author);
            text = itemView.findViewById(R.id.text);
            subject = itemView.findViewById(R.id.subject1);
            date = itemView.findViewById(R.id.dateStart);
            profile_user = itemView.findViewById(R.id.profile1_user);
        }

        @Override
        public void onListener() {
            cardView.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Open resume", Toast.LENGTH_LONG).show();
                v.getContext().startActivity(new Intent(v.getContext(), ResumeActivity.class));
            });

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
